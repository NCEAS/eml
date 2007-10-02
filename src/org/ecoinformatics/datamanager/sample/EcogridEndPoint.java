package org.ecoinformatics.datamanager.sample;


import org.ecoinformatics.datamanager.download.EcogridEndPointInterface;


/**
 * This class implements EcogridEndPointInterface for use by the sample
 * calling application when either downloading data to a local data store,
 * or when loading data to the database.
 * 
 * The list of methods required to be implemented by that interface are:
 * 
 * getMetacatEcogridEndPoint()
 * getSRBEcogridEndPoint()
 * getSRBMachineName()
 * 
 * @author tao
 *
 */
public class EcogridEndPoint implements EcogridEndPointInterface 
{
     /**
        * Gets the end point which Metacat implements ecogrid interface.
        * This end point will be used to handle ecogrid protocol
        * @return end point url string
        */
       public String getMetacatEcogridEndPoint()
       {
           return "http://localhost:8080/knb/services/EcoGridQuery";
       }
       
       /**
        * Gets the end point which SRB implements ecogrid interface
        * This end point will be used to handle srb protocol
        * @return end point url string
        */
       public String getSRBEcogridEndPoint()
       {
           return "http://srbbrick8.sdsc.edu:8080/SRBImpl/services/SRBQueryService";
       }
       
       /**
        * Gets the machine name which srb protocol will be used.
        * The default value for this class is "srb-mcat.sdsc.edu"
        * @return the machine name of srb server
        */
       public String getSRBMachineName()
       {
           return "srb-mcat.sdsc.edu";
       }
    
}

