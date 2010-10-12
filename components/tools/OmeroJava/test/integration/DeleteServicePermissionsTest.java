/*
 * $Id$
 *
 *   Copyright 2006-2010 University of Dundee. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */
package integration;

//Java imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//Third-party libraries
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

//Application-internal dependencies
import omero.api.IAdminPrx;
import omero.api.IRenderingSettingsPrx;
import omero.api.delete.DeleteCommand;
import omero.model.Dataset;
import omero.model.Experimenter;
import omero.model.ExperimenterGroup;
import omero.model.Image;
import omero.model.ImageAnnotationLink;
import omero.model.ImageAnnotationLinkI;
import omero.model.Pixels;
import omero.model.Plate;
import omero.model.Project;
import omero.model.RenderingDef;
import omero.model.Screen;
import omero.model.TagAnnotation;
import omero.model.TagAnnotationI;
import omero.sys.EventContext;
import omero.sys.ParametersI;


/** 
 * Collections of tests for the <code>Delete</code> service related to permissions.
 * Unlike {@link DeleteServiceTest} we are <em>not</em> creating a new user before
 * each method since these methods need multiple users in specific groups.
 *
 * @since 4.2.1
 */
@Test(groups = {"delete", "integration"})
public class DeleteServicePermissionsTest 
	extends AbstractTest
{

    /**
     * Since we are creating a new client on each invocation, we should also
     * clean it up. Note: {@link #newUserAndGroup(String)} also closes, but
     * not the very last invocation.
     */
    @AfterMethod
    public void close() 
    	throws Exception
    {
        clean();
    }
    
    /**
     * Test to try to delete an (top) object owned by another user in a 
     * private group i.e. RW----.
     * @throws Exception Thrown if an error occurred.     
     */
    @Test
    public void testDeleteObjectOwnedByOtherRW()
    	throws Exception
    {

    	EventContext user1Ctx = newUserAndGroup("rwrw--");
    	omero.client user1 = client;

    	//Image
    	Image img = (Image) iUpdate.saveAndReturnObject(
    			mmFactory.createImage());
    	//Dataset
    	Dataset d = (Dataset) iUpdate.saveAndReturnObject(
    			mmFactory.simpleDatasetData().asDataset());

    	//Project
    	Project p = (Project) iUpdate.saveAndReturnObject(
    			mmFactory.simpleProjectData().asProject());

    	//Dataset
    	Screen s = (Screen) iUpdate.saveAndReturnObject(
    			mmFactory.simpleScreenData().asScreen());

    	//Dataset
    	Plate plate = (Plate) iUpdate.saveAndReturnObject(
    			mmFactory.simplePlateData().asPlate());

    	// other user tries to delete
    	disconnect();
    	newUserInGroup(user1Ctx);

    	DeleteCommand[] dcs = new DeleteCommand[5];
    	dcs[0] = new DeleteCommand(DeleteServiceTest.REF_IMAGE,
    			img.getId().getValue(), null);
    	dcs[1] = new DeleteCommand(DeleteServiceTest.REF_DATASET,
    			d.getId().getValue(), null);
    	dcs[2] = new DeleteCommand(DeleteServiceTest.REF_PROJECT,
    			p.getId().getValue(), null);
    	dcs[3] = new DeleteCommand(DeleteServiceTest.REF_SCREEN,
    			s.getId().getValue(), null);
    	dcs[4] = new DeleteCommand(DeleteServiceTest.REF_PLATE,
    			plate.getId().getValue(), null);
    	delete(iDelete, client, dcs);

    	// Now log the original user back in
    	disconnect();
    	init(user1);

    	assertExists(img);
    	assertExists(d);
    	assertExists(p);
    	assertExists(s);
    	assertExists(plate);	
    }
    
    /**
     * Test to try to delete an image owned by another user in a collaborative
     * group i.e. RWR---
     * @throws Exception Thrown if an error occurred.     
     */
    @Test
    public void testDeleteImageOwnedByOtherRWR()
    	throws Exception
    {

    	// set up collaborative group and one user, "the owner"
    	newUserAndGroup("rwr---");

    	// create an owner who then creates the image
    	Image img = (Image) iUpdate.saveAndReturnObject(
    			mmFactory.simpleImage(0));
    	long imageID = img.getId().getValue();

    	// create another user and try to delete the image
    	newUserInGroup();
    	delete(client, new DeleteCommand(
    			DeleteServiceTest.REF_IMAGE, imageID, null));

    	// check the image exists as the owner
    	assertExists(img);
    }

    /**
     * Test to try to delete an object by the administrator in a private group
     * i.e. RW----
     * @throws Exception Thrown if an error occurred.     
     */
    @Test
    public void testDeleteObjectByAdmin()
    	throws Exception
    {
        // Create the private group
        newUserAndGroup("rw----");

        // Create the data as the user
        Image img = (Image) iUpdate.saveAndReturnObject(
				mmFactory.createImage());

        // Log the admin into that users group
        logRootIntoGroup();
        delete(client, new DeleteCommand(
    			DeleteServiceTest.REF_IMAGE, img.getId().getValue(), null));

        assertDoesNotExist(img);
    }

    /**
     * Test to try to delete an object by the owner of a private group
     * i.e. RW----
     * @throws Exception Thrown if an error occurred.     
     */
    @Test
    public void testDeleteObjectByGroupOwner()
    	throws Exception
    {
        EventContext ownerEc = newUserAndGroup("rw----");

    	//owner creates the image
		Image img = (Image) iUpdate.saveAndReturnObject(
				mmFactory.createImage());
		
    	//group owner deletes it
		disconnect();
		newUserInGroup(ownerEc);
		makeGroupOwner();

		delete(iDelete, client, new DeleteCommand(
    			DeleteServiceTest.REF_IMAGE, img.getId().getValue(), null));

		assertDoesNotExist(img);
    }
    
    /**
     * Test to try to delete an object by the administrator in a read-only
     * collaborative group i.e. RWR---
     * @throws Exception Thrown if an error occurred.     
     */
    @Test
    public void testDeleteObjectByAdminRWR()
    	throws Exception
    {

        // set up collaborative group
        newUserAndGroup("rwr---");

        // owner creates the image
		Image img = (Image) iUpdate.saveAndReturnObject(
				mmFactory.createImage());
		
		//admin deletes the object.
		logRootIntoGroup();
		delete(client, new DeleteCommand(
    			DeleteServiceTest.REF_IMAGE, img.getId().getValue(), null));

		assertDoesNotExist(img);
    }
    
	/**
     * Test to delete an image tagged collaboratively by another user.
     * @throws Exception Thrown if an error occurred.
     */
    @Test(groups = {"ticket:2881"})
    public void testDeleteTaggedImageTagOwnedByOther() 
    	throws Exception
    {
        // set up collaborative group with an "owner" user
        EventContext ec = newUserAndGroup("rwrw--");

        // owner creates the image
        Image img = (Image) iUpdate.saveAndReturnObject(
        		mmFactory.simpleImage(0));
        
        // tagger creates tag and tags the image
        newUserInGroup(ec);
        
        TagAnnotation c = new TagAnnotationI();
    	c.setTextValue(omero.rtypes.rstring("tag"));
    	c = (TagAnnotation) iUpdate.saveAndReturnObject(c);
    	ImageAnnotationLink link = new ImageAnnotationLinkI();
    	link.setParent(img);
    	link.setChild(new TagAnnotationI(c.getId().getValue(), false));		
    	link = (ImageAnnotationLink) iUpdate.saveAndReturnObject(link);


    	// owner tries to delete image.
    	loginUser(ec);
    	long id = img.getId().getValue();
    	delete(client, new DeleteCommand(DeleteServiceTest.REF_IMAGE, id, 
    			null));

    	assertDoesNotExist(img);
    	assertExists(c);
    }
    
	/**
     * Test to delete a tag used by another user.
     * @throws Exception Thrown if an error occurred.
     */
    @Test(groups = "ticket:2962")
    public void testDeleteTagUsedByOther() 
    	throws Exception
    {
    	// set up collaborative group with an "owner" user
        EventContext ec = newUserAndGroup("rwrw--");

        // owner creates the image
        Image img = (Image) iUpdate.saveAndReturnObject(
        		mmFactory.simpleImage(0));

        omero.client owner = disconnect();
        
        // tagger creates tag
        newUserInGroup(ec);
        
        TagAnnotation c = new TagAnnotationI();
    	c.setTextValue(omero.rtypes.rstring("tag"));
    	c = (TagAnnotation) iUpdate.saveAndReturnObject(c);
    	omero.client tagger = disconnect();
    	init(owner);
    	
    	//Image's owner tags the image.
    	ImageAnnotationLink link = new ImageAnnotationLinkI();
    	link.setParent(img);
    	link.setChild(new TagAnnotationI(c.getId().getValue(), false));		
    	link = (ImageAnnotationLink) iUpdate.saveAndReturnObject(link);
    	
    	//Tag's owner now deletes the tag.
    	init(tagger);
    	delete(false, iDelete, client, new DeleteCommand(
    			DeleteServiceTest.REF_ANN,
    			c.getId().getValue(), null));
    	assertExists(c);
    	assertExists(link);
    	assertExists(img);
    }
    
    /**
     * Test to delete a tag used by another user. The tag is owned by the 
     * group owner.
     * @throws Exception Thrown if an error occurred.
     */
    @Test(enabled = false, groups = "ticket:2962")
    public void testDeleteTagOwnedByGroupOwnerUsedByOther() 
    	throws Exception
    {
    	// set up collaborative group with an "owner" user
        EventContext ec = newUserAndGroup("rwrw--");

        // owner creates the image
        Image img = (Image) iUpdate.saveAndReturnObject(
        		mmFactory.simpleImage(0));

        omero.client owner = disconnect();
        
        // tagger creates tag
        ec = newUserInGroup(ec);
        //make the tagger the owner of the group.
        IAdminPrx rootAdmin = root.getSession().getAdminService();
        Experimenter exp = rootAdmin.lookupExperimenter(ec.userName);
        ExperimenterGroup group = rootAdmin.lookupGroup(ec.groupName);
        rootAdmin.setGroupOwner(group, exp);
        
        
        TagAnnotation c = new TagAnnotationI();
    	c.setTextValue(omero.rtypes.rstring("tag"));
    	c = (TagAnnotation) iUpdate.saveAndReturnObject(c);
    	omero.client tagger = disconnect();
    	
    	init(owner);
    	
    	//Image's owner tags the image.
    	ImageAnnotationLink link = new ImageAnnotationLinkI();
    	link.setParent(img);
    	link.setChild(new TagAnnotationI(c.getId().getValue(), false));		
    	link = (ImageAnnotationLink) iUpdate.saveAndReturnObject(link);
    	
    	//Tag's owner now deletes the tag.
    	init(tagger);
    	delete(false, iDelete, client, new DeleteCommand(
    			DeleteServiceTest.REF_ANN,
    			c.getId().getValue(), null));
    	assertExists(c); //fail tag is deleted
    	assertExists(link); //fail link is deleted
    	assertExists(img);
    }
    
	/**
     * Test to delete an image viewed by another user in a RWRW-- group.
     * @throws Exception Thrown if an error occurred.
     */
    @Test(groups = "ticket:2963")
    public void testDeleteImageViewedByOtherRenderingSettingsOnlyRWRW()
    	throws Exception	
    {
    	EventContext ownerCtx = newUserAndGroup("rwrw--");
    	//owner creates the image
    	Image image = (Image) iUpdate.saveAndReturnObject(
    			mmFactory.createImage());
    	//create rendering settings for that user.
    	Pixels pixels = image.getPrimaryPixels();
    	long id = pixels.getId().getValue();
    	long imageID = image.getId().getValue();
    	//Image
    	List<Long> ids = new ArrayList<Long>();
    	ids.add(imageID);
    	//method already tested 
    	IRenderingSettingsPrx prx = factory.getRenderingSettingsService();
    	prx.resetDefaultsInSet(Image.class.getName(), ids);
	RenderingDef ownerDef = factory.getPixelsService().retrieveRndSettings(id);

    	newUserInGroup(ownerCtx);
    	prx = factory.getRenderingSettingsService();
    	prx.resetDefaultsInSet(Image.class.getName(), ids);
	RenderingDef otherDef = factory.getPixelsService().retrieveRndSettings(id);
	assertAllExist(ownerDef, otherDef);
    	disconnect();

    	//Delete the image.
	loginUser(ownerCtx);
    	delete(client, new DeleteCommand(
    			DeleteServiceTest.REF_IMAGE, imageID, null));
	assertNoneExist(image, ownerDef, otherDef);

    }
    
}
