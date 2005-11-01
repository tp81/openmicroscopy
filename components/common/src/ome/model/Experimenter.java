package ome.model;

import ome.util.BaseModelUtils;
import ome.util.Filterable;
import ome.util.Filter;


import java.util.*;




/**
 * Experimenter generated by hbm2java
 */
public class
Experimenter 
implements java.io.Serializable ,
ome.api.OMEModel,
ome.util.Filterable {

    // Fields    

     private Integer attributeId;
     private String omeName;
     private String email;
     private String firstname;
     private String dataDir;
     private String lastname;
     private String institution;
     private Set renderingSettings;
     private Set datasets;
     private Set groupsByLeader;
     private Set groupsByContact;
     private Set analysisChains;
     private Set images;
     private Set analysisChainExecutions;
     private Set projects;
     private Set omeSessions;
     private Set moduleExecutions;
     private Group group;
     private ModuleExecution moduleExecution;
     private Set groups;


    // Constructors

    /** default constructor */
    public Experimenter() {
    }
    
    /** constructor with id */
    public Experimenter(Integer attributeId) {
        this.attributeId = attributeId;
    }
   
    
    

    // Property accessors

    /**
     * 
     */
    public Integer getAttributeId() {
        return this.attributeId;
    }
    
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    /**
     * 
     */
    public String getOmeName() {
        return this.omeName;
    }
    
    public void setOmeName(String omeName) {
        this.omeName = omeName;
    }

    /**
     * 
     */
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     */
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * 
     */
    public String getDataDir() {
        return this.dataDir;
    }
    
    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * 
     */
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * 
     */
    public String getInstitution() {
        return this.institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * 
     */
    public Set getRenderingSettings() {
        return this.renderingSettings;
    }
    
    public void setRenderingSettings(Set renderingSettings) {
        this.renderingSettings = renderingSettings;
    }

    /**
     * 
     */
    public Set getDatasets() {
        return this.datasets;
    }
    
    public void setDatasets(Set datasets) {
        this.datasets = datasets;
    }

    /**
     * 
     */
    public Set getGroupsByLeader() {
        return this.groupsByLeader;
    }
    
    public void setGroupsByLeader(Set groupsByLeader) {
        this.groupsByLeader = groupsByLeader;
    }

    /**
     * 
     */
    public Set getGroupsByContact() {
        return this.groupsByContact;
    }
    
    public void setGroupsByContact(Set groupsByContact) {
        this.groupsByContact = groupsByContact;
    }

    /**
     * 
     */
    public Set getAnalysisChains() {
        return this.analysisChains;
    }
    
    public void setAnalysisChains(Set analysisChains) {
        this.analysisChains = analysisChains;
    }

    /**
     * 
     */
    public Set getImages() {
        return this.images;
    }
    
    public void setImages(Set images) {
        this.images = images;
    }

    /**
     * 
     */
    public Set getAnalysisChainExecutions() {
        return this.analysisChainExecutions;
    }
    
    public void setAnalysisChainExecutions(Set analysisChainExecutions) {
        this.analysisChainExecutions = analysisChainExecutions;
    }

    /**
     * 
     */
    public Set getProjects() {
        return this.projects;
    }
    
    public void setProjects(Set projects) {
        this.projects = projects;
    }

    /**
     * 
     */
    public Set getOmeSessions() {
        return this.omeSessions;
    }
    
    public void setOmeSessions(Set omeSessions) {
        this.omeSessions = omeSessions;
    }

    /**
     * 
     */
    public Set getModuleExecutions() {
        return this.moduleExecutions;
    }
    
    public void setModuleExecutions(Set moduleExecutions) {
        this.moduleExecutions = moduleExecutions;
    }

    /**
     * 
     */
    public Group getGroup() {
        return this.group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * 
     */
    public ModuleExecution getModuleExecution() {
        return this.moduleExecution;
    }
    
    public void setModuleExecution(ModuleExecution moduleExecution) {
        this.moduleExecution = moduleExecution;
    }

    /**
     * 
     */
    public Set getGroups() {
        return this.groups;
    }
    
    public void setGroups(Set groups) {
        this.groups = groups;
    }






  public boolean acceptFilter(Filter filter){


	  // Visiting: AttributeId ------------------------------------------
	  Integer _AttributeId = null;
	  try {
	     _AttributeId = getAttributeId();
	  } catch (Exception e) {
		 setAttributeId(null);
	  }
// TODO catch class cast?
	  setAttributeId((Integer) filter.filter(ATTRIBUTEID,_AttributeId)); 

	  // Visiting: OmeName ------------------------------------------
	  String _OmeName = null;
	  try {
	     _OmeName = getOmeName();
	  } catch (Exception e) {
		 setOmeName(null);
	  }
// TODO catch class cast?
	  setOmeName((String) filter.filter(OMENAME,_OmeName)); 

	  // Visiting: Email ------------------------------------------
	  String _Email = null;
	  try {
	     _Email = getEmail();
	  } catch (Exception e) {
		 setEmail(null);
	  }
// TODO catch class cast?
	  setEmail((String) filter.filter(EMAIL,_Email)); 

	  // Visiting: Firstname ------------------------------------------
	  String _Firstname = null;
	  try {
	     _Firstname = getFirstname();
	  } catch (Exception e) {
		 setFirstname(null);
	  }
// TODO catch class cast?
	  setFirstname((String) filter.filter(FIRSTNAME,_Firstname)); 

	  // Visiting: DataDir ------------------------------------------
	  String _DataDir = null;
	  try {
	     _DataDir = getDataDir();
	  } catch (Exception e) {
		 setDataDir(null);
	  }
// TODO catch class cast?
	  setDataDir((String) filter.filter(DATADIR,_DataDir)); 

	  // Visiting: Lastname ------------------------------------------
	  String _Lastname = null;
	  try {
	     _Lastname = getLastname();
	  } catch (Exception e) {
		 setLastname(null);
	  }
// TODO catch class cast?
	  setLastname((String) filter.filter(LASTNAME,_Lastname)); 

	  // Visiting: Institution ------------------------------------------
	  String _Institution = null;
	  try {
	     _Institution = getInstitution();
	  } catch (Exception e) {
		 setInstitution(null);
	  }
// TODO catch class cast?
	  setInstitution((String) filter.filter(INSTITUTION,_Institution)); 

	  // Visiting: RenderingSettings ------------------------------------------
	  Set _RenderingSettings = null;
	  try {
	     _RenderingSettings = getRenderingSettings();
	  } catch (Exception e) {
		 setRenderingSettings(null);
	  }
// TODO catch class cast?
	  setRenderingSettings((Set) filter.filter(RENDERINGSETTINGS,_RenderingSettings)); 

	  // Visiting: Datasets ------------------------------------------
	  Set _Datasets = null;
	  try {
	     _Datasets = getDatasets();
	  } catch (Exception e) {
		 setDatasets(null);
	  }
// TODO catch class cast?
	  setDatasets((Set) filter.filter(DATASETS,_Datasets)); 

	  // Visiting: GroupsByLeader ------------------------------------------
	  Set _GroupsByLeader = null;
	  try {
	     _GroupsByLeader = getGroupsByLeader();
	  } catch (Exception e) {
		 setGroupsByLeader(null);
	  }
// TODO catch class cast?
	  setGroupsByLeader((Set) filter.filter(GROUPSBYLEADER,_GroupsByLeader)); 

	  // Visiting: GroupsByContact ------------------------------------------
	  Set _GroupsByContact = null;
	  try {
	     _GroupsByContact = getGroupsByContact();
	  } catch (Exception e) {
		 setGroupsByContact(null);
	  }
// TODO catch class cast?
	  setGroupsByContact((Set) filter.filter(GROUPSBYCONTACT,_GroupsByContact)); 

	  // Visiting: AnalysisChains ------------------------------------------
	  Set _AnalysisChains = null;
	  try {
	     _AnalysisChains = getAnalysisChains();
	  } catch (Exception e) {
		 setAnalysisChains(null);
	  }
// TODO catch class cast?
	  setAnalysisChains((Set) filter.filter(ANALYSISCHAINS,_AnalysisChains)); 

	  // Visiting: Images ------------------------------------------
	  Set _Images = null;
	  try {
	     _Images = getImages();
	  } catch (Exception e) {
		 setImages(null);
	  }
// TODO catch class cast?
	  setImages((Set) filter.filter(IMAGES,_Images)); 

	  // Visiting: AnalysisChainExecutions ------------------------------------------
	  Set _AnalysisChainExecutions = null;
	  try {
	     _AnalysisChainExecutions = getAnalysisChainExecutions();
	  } catch (Exception e) {
		 setAnalysisChainExecutions(null);
	  }
// TODO catch class cast?
	  setAnalysisChainExecutions((Set) filter.filter(ANALYSISCHAINEXECUTIONS,_AnalysisChainExecutions)); 

	  // Visiting: Projects ------------------------------------------
	  Set _Projects = null;
	  try {
	     _Projects = getProjects();
	  } catch (Exception e) {
		 setProjects(null);
	  }
// TODO catch class cast?
	  setProjects((Set) filter.filter(PROJECTS,_Projects)); 

	  // Visiting: OmeSessions ------------------------------------------
	  Set _OmeSessions = null;
	  try {
	     _OmeSessions = getOmeSessions();
	  } catch (Exception e) {
		 setOmeSessions(null);
	  }
// TODO catch class cast?
	  setOmeSessions((Set) filter.filter(OMESESSIONS,_OmeSessions)); 

	  // Visiting: ModuleExecutions ------------------------------------------
	  Set _ModuleExecutions = null;
	  try {
	     _ModuleExecutions = getModuleExecutions();
	  } catch (Exception e) {
		 setModuleExecutions(null);
	  }
// TODO catch class cast?
	  setModuleExecutions((Set) filter.filter(MODULEEXECUTIONS,_ModuleExecutions)); 

	  // Visiting: Group ------------------------------------------
	  Group _Group = null;
	  try {
	     _Group = getGroup();
	  } catch (Exception e) {
		 setGroup(null);
	  }
// TODO catch class cast?
	  setGroup((Group) filter.filter(GROUP,_Group)); 

	  // Visiting: ModuleExecution ------------------------------------------
	  ModuleExecution _ModuleExecution = null;
	  try {
	     _ModuleExecution = getModuleExecution();
	  } catch (Exception e) {
		 setModuleExecution(null);
	  }
// TODO catch class cast?
	  setModuleExecution((ModuleExecution) filter.filter(MODULEEXECUTION,_ModuleExecution)); 

	  // Visiting: Groups ------------------------------------------
	  Set _Groups = null;
	  try {
	     _Groups = getGroups();
	  } catch (Exception e) {
		 setGroups(null);
	  }
// TODO catch class cast?
	  setGroups((Set) filter.filter(GROUPS,_Groups)); 
   	 return true;
  }
  
  public String toString(){
	return "Experimenter"+(attributeId==null ? ":Hash_"+this.hashCode() : ":Id_"+attributeId);
  }
  
  // FIELD-FIELDS
  
	public final static String ATTRIBUTEID = "Experimenter_AttributeId";
	public final static String OMENAME = "Experimenter_OmeName";
	public final static String EMAIL = "Experimenter_Email";
	public final static String FIRSTNAME = "Experimenter_Firstname";
	public final static String DATADIR = "Experimenter_DataDir";
	public final static String LASTNAME = "Experimenter_Lastname";
	public final static String INSTITUTION = "Experimenter_Institution";
	public final static String RENDERINGSETTINGS = "Experimenter_RenderingSettings";
	public final static String DATASETS = "Experimenter_Datasets";
	public final static String GROUPSBYLEADER = "Experimenter_GroupsByLeader";
	public final static String GROUPSBYCONTACT = "Experimenter_GroupsByContact";
	public final static String ANALYSISCHAINS = "Experimenter_AnalysisChains";
	public final static String IMAGES = "Experimenter_Images";
	public final static String ANALYSISCHAINEXECUTIONS = "Experimenter_AnalysisChainExecutions";
	public final static String PROJECTS = "Experimenter_Projects";
	public final static String OMESESSIONS = "Experimenter_OmeSessions";
	public final static String MODULEEXECUTIONS = "Experimenter_ModuleExecutions";
	public final static String GROUP = "Experimenter_Group";
	public final static String MODULEEXECUTION = "Experimenter_ModuleExecution";
	public final static String GROUPS = "Experimenter_Groups";
 	public final static Set FIELDS = new HashSet();
	static {
	   FIELDS.add(ATTRIBUTEID);
	   FIELDS.add(OMENAME);
	   FIELDS.add(EMAIL);
	   FIELDS.add(FIRSTNAME);
	   FIELDS.add(DATADIR);
	   FIELDS.add(LASTNAME);
	   FIELDS.add(INSTITUTION);
	   FIELDS.add(RENDERINGSETTINGS);
	   FIELDS.add(DATASETS);
	   FIELDS.add(GROUPSBYLEADER);
	   FIELDS.add(GROUPSBYCONTACT);
	   FIELDS.add(ANALYSISCHAINS);
	   FIELDS.add(IMAGES);
	   FIELDS.add(ANALYSISCHAINEXECUTIONS);
	   FIELDS.add(PROJECTS);
	   FIELDS.add(OMESESSIONS);
	   FIELDS.add(MODULEEXECUTIONS);
	   FIELDS.add(GROUP);
	   FIELDS.add(MODULEEXECUTION);
	   FIELDS.add(GROUPS);
 	}


}
