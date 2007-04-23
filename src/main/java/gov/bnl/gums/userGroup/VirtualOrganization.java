package gov.bnl.gums.userGroup;

import gov.bnl.gums.configuration.Configuration;
import gov.bnl.gums.db.UserGroupDB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * Represents a VOMS server.  Contains the other parameters for access this server. 
 * This is meant to be a shared reference for multiple user groups.  One specifies
 * the base url here and provides the remainder url for each user group.
 *
 * @author Jay Packard
 */
public class VirtualOrganization {
    private Log log = LogFactory.getLog(VOMSUserGroup.class);
	private String name = "";
	private String description = "";
    private String baseUrl = "";
    private String sslKey = "";
    private String sslCertfile = "";
    private String sslCAFiles = "";
    private String sslKeyPasswd = "";
    private UserGroupDB db = null;
	private String persistenceFactory;
	private Configuration configuration = null;
    
	/**
	 * Creates a new virtual organization object. This empty 
	 * constructor is needed by the XML Digestor.
	 */
	public VirtualOrganization() {
	}

	/**
	 * Creates a new virtual organization object with a configuration.
	 * 
	 * @param configuration
	 */
	public VirtualOrganization(Configuration configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Creates a new virtual organization object with a configuration and a name.
	 * 
	 * @param configuration
	 * @param name
	 */
	public VirtualOrganization(Configuration configuration, String name) {
		this.configuration = configuration;
		this.name = name;
	}
	 
	
	public VirtualOrganization clone(Configuration configuration) {
    	VirtualOrganization virtualOrganization = new VirtualOrganization(configuration, name);
    	virtualOrganization.setDescription(getDescription());
    	virtualOrganization.setBaseUrl(baseUrl);
    	virtualOrganization.setSslKey(sslKey);
    	virtualOrganization.setSslCertfile(sslCertfile);
    	virtualOrganization.setSslKeyPasswd(sslKeyPasswd);
    	virtualOrganization.setSslCAFiles(sslCAFiles);
    	virtualOrganization.setPersistenceFactory(persistenceFactory);
    	return virtualOrganization;
    }

	/**
     * Returns the address of the VOMS server to contact.
     * @return The address of the VOMS server (i.e. https://voms.myste.org:8443/edg-voms-admin/myvo/services/VOMSAdmin)
     */
    public String getBaseUrl() {
        return baseUrl;
    }
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public String getDescription() {
		return description;
	}
	
	public UserGroupDB getDB() {
    	if (db==null)
            db = configuration.getPersistenceFactory(persistenceFactory).retrieveUserGroupDB( getName() );
    	return db;
    }
	
    public String getName() {
		return name;
	}
    
    public String getPersistenceFactory() {
        return persistenceFactory;
    }
    
    /**
     * Returns the location of the Certificate Authority certificates used to connect to the VOMS server.
     * @return The location of the CA certificates (i.e. /etc/grid-security/certificates/*.0)
     */
    public String getSslCAFiles() {
        return this.sslCAFiles;
    }
    
    /**
     * Returns the location of the certificated used to connect to the VOMS server.
     * @return The location of the certificate (i.e. /etc/grid-security/hostcert.pem)
     */
    public String getSslCertfile() {
        return this.sslCertfile;
    }
    
    /**
     * Returns the location of the key to be used during the connection.
     * @return The location of the key (i.e. /etc/grid-security/hostkey.pem)
     */
    public String getSslKey() {
        return this.sslKey;
    }
    
    /**
     * Returns the private key password used to connect to the VOMS server.
     * @return The password for the private key
     */
    public String getSslKeyPasswd() {
        return this.sslKeyPasswd;
    }
    
    /**
     * Changes the base address of the VOMS server to contact. The address of the server
     * is really the address of the Web service components. If you go to that address,
     * the AXIS servlet has to reply.
     * @param url The address of the VOMS server (i.e. https://voms.myste.org:8443/edg-voms-admin)
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    public void setPersistenceFactory(String persistenceFactory) {
        this.persistenceFactory = persistenceFactory;
    }
    
    /**
     * Changes the location of the Certificate Authority certificates used to connect to the VOMS server.
     * @param sslCAFiles The location of the CA certificates (i.e. /etc/grid-security/certificates/*.0)
     */
    public void setSslCAFiles(String sslCAFiles) {
        this.sslCAFiles = sslCAFiles;
    }
    
    /**
     * Changes the location of the certificated used to connect to the VOMS server.
     * @param sslCertfile The location of the certificate (i.e. /etc/grid-security/hostcert.pem)
     */
    public void setSslCertfile(String sslCertfile) {
        this.sslCertfile = sslCertfile;
    }
    
    /**
     * Changes the location of the private key used to connect to the VOMS server.
     * @param sslKey The location of the key (i.e. /etc/grid-security/hostkey.pem)
     */
    public void setSslKey(String sslKey) {
        this.sslKey = sslKey;
    }
    
    /**
     * Changes the private key password used to connect to the VOMS server.
     * @param sslKeyPasswd The password for the private key
     */
    public void setSslKeyPasswd(String sslKeyPasswd) {
        this.sslKeyPasswd = sslKeyPasswd;
    }

    public String toString() {
        return "VirtualOrganization: baseUrl='" + baseUrl + "' sslCertfile='" + getSslCertfile() + "' sslKey='" + getSslKey() + "' sslKeyPasswd set:" + (!getSslKeyPasswd().equals("")) + " - sslCAFiles='" + getSslCAFiles() + "'";
    }
    
    public String toXML() {
    	String retStr = "\t\t<virtualOrganization\n"+
		"\t\t\tname='"+name+"'\n"+
		"\t\t\tdescription='"+getDescription()+"'\n"+
		"\t\t\tpersistenceFactory='"+persistenceFactory+"'\n"+
		"\t\t\tbaseUrl='"+baseUrl+"'\n";
    	if (sslKey != null)
    		retStr += "\t\t\tsslKey='"+sslKey+"'\n";
    	if (sslCertfile != null)
    		retStr += "\t\t\tsslCertfile='"+sslCertfile+"'\n";
    	if (sslKeyPasswd != null)
    		retStr += "\t\t\tsslKeyPasswd='"+sslKeyPasswd+"'\n";
    	if (sslCAFiles != null)
    		retStr += "\t\t\tsslCAFiles='"+sslCAFiles+"'\n";

    	if (retStr.charAt(retStr.length()-1)=='\n')
    		retStr = retStr.substring(0, retStr.length()-1);    

    	retStr += "/>\n\n";    	
    	
    	return retStr;
    }
    
}
