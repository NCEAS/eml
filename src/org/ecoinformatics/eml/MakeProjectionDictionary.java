/**
 *     '$RCSfile: MakeProjectionDictionary.java,v $'
 *     Copyright: 1997-2002 Regents of the University of California,
 *                          University of New Mexico, and
 *                          Arizona State University
 *      Sponsors: National Center for Ecological Analysis and Synthesis and
 *                Partnership for Interdisciplinary Studies of Coastal Oceans,
 *                   University of California Santa Barbara
 *                Long-Term Ecological Research Network Office,
 *                   University of New Mexico
 *                Center for Environmental Studies, Arizona State University
 * Other funding: National Science Foundation (see README for details)
 *                The David and Lucile Packard Foundation
 *   For Details: http://knb.ecoinformatics.org/
 *
 *      '$Author: berkley $'
 *        '$Date: 2002-10-03 21:36:17 $'
 *    '$Revision: 1.2 $'
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.ecoinformatics.eml;

import java.io.*;
import java.lang.*;
import java.util.*;
import org.jdom.*;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author  mccartney
 *
 * MakeProjectionDictionary.java
 *
 * This class walks a directory tree searching for ESRI projection definition
 * files (*.prj). It parses the projection information and writes it to an XML
 * file that conforms to the eml-spatialReference.xsd schema.
 *
 * usage: MakeProjectionDictionary path
 *      path= root folder to search for prj files
 */
public class MakeProjectionDictionary {


    public static Element root = new Element("projectionList", Namespace.getNamespace("sp", "eml://ecoinformatics.org/spatialReference-2.0.0rc1"));
    public static Document XMLDoc = new Document(root);


    public static void main(String args[]) throws IOException {

        root.setAttribute("schemaLocation","eml://ecoinformatics.org/spatialReference-2.0.0rc1 eml-spatialReference.xsd",Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance"));
        if (args.length==1){
            processPrjFiles(args[0]);

            //       processPrjFiles("c:\\ESRI\\arcGIS\\arcexe82\\coordinate systems");

            File outFilePath= new File(args[0].concat("\\eml-spatialReferenceDictionary.xml"));
            FileWriter writer = new FileWriter(outFilePath);
            XMLOutputter outputter = new XMLOutputter("  ", true);
            outputter.output(XMLDoc, writer);
        }else{
            System.out.print("usage: java MakeProjectionDictionary path\n");
        }
    }

    //build projection list
    public static void processPrjFiles(String filePath){
        try{


            File file = new File(filePath);

            if (file.isDirectory()){
                File[] files = file.listFiles();
                for ( int i=0;i<files.length;i++){
                    processPrjFiles(files[i].getPath());
                }

            } else {

                if (file.getName().endsWith(".prj")){
                    addProjection(file.getPath(),root);
                }
            }


        } catch(Exception e){
            System.out.println(e.getMessage());}
    }



    public static void addProjection(String filePath, Element root){

        File file = new File(filePath);
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));


            // read the first line and tokenize it
            String readline=br.readLine();
            int i=0;
            StringTokenizer tokens=new StringTokenizer(readline, "[]\",", false);

            //start a new horizsys element
            Element horizsys = new Element("horizCoordSysDef");
            int howManyTokens = tokens.countTokens();

            if (tokens.nextToken().equalsIgnoreCase("PROJCS")) {
                Element projcs = new Element("projCoordSys");
                //set name attribute
                horizsys.setAttribute("name",tokens.nextToken());

                if (tokens.nextToken().equalsIgnoreCase("GEOGCS")) {
                    Element geogcs = new Element("geogCoordSys");
                    geogcs.setAttribute("name", tokens.nextToken());
                    if (tokens.nextToken().equalsIgnoreCase("DATUM")){
                        geogcs.addContent(new Element("datum").setAttribute("name",tokens.nextToken()));
                    } else {
                        System.out.print("Didnt find DATUM\n");
                    }

                    if (tokens.nextToken().equalsIgnoreCase("SPHEROID")){
                        Element spheroid = new Element("spheroid");
                        spheroid.setAttribute("name",tokens.nextToken());
                        spheroid.setAttribute("semiAxisMajor",tokens.nextToken());
                        spheroid.setAttribute("denomFlatRatio",tokens.nextToken());
                        geogcs.addContent(spheroid);
                    } else {
                        System.out.print("Didnt find SPHEROID\n");
                    }
                    if (tokens.nextToken().equalsIgnoreCase("PRIMEM")){
                        Element primem=new Element("primeMeridian");
                        primem.setAttribute("name",tokens.nextToken());
                        primem.setAttribute("longitude",tokens.nextToken());
                        geogcs.addContent(primem);
                    } else {
                        System.out.print("Didnt find PRIMEM\n");

                    }
                    if (tokens.nextToken().equalsIgnoreCase("UNIT")){
                        Element unit=new Element("unit");
                        unit.setAttribute("name", tokens.nextToken());
                        unit.setAttribute("radiansPerUnit",tokens.nextToken());
                        geogcs.addContent(unit);
                    } else {
                        System.out.print("Didnt find UNIT\n");

                    }
                    projcs.addContent(geogcs);

                } else{
                    System.out.print("Didnt find GEOGCS\n");
                }


                if (tokens.nextToken().equalsIgnoreCase("PROJECTION")){  //planar/mapproj/ or planar/gridsys
                    Element project = new Element("projection");
                    project.setAttribute("name",tokens.nextToken());

/*dont need this if we just used the same names
               for (i=0;i<projNames.length/2;i++){
                    if (projNames[i][0]==projName){
                        Element projElem= new Element(projNames[i][1]);
                        i=projNames.length/2;
                    }

                }
 */
                    while (tokens.nextToken().equalsIgnoreCase("PARAMETER")) {
                        Element param = new Element("parameter");
                        param.setAttribute("name",tokens.nextToken());
                        param.setAttribute("value",tokens.nextToken());
                        project.addContent(param);
                    }
                        Element unit = new Element("unit");
                        unit.setAttribute("name",tokens.nextToken());
                        unit.setAttribute("metersPerUnit",tokens.nextToken());
                        project.addContent(unit);

                    projcs.addContent(project);

                }
                horizsys.addContent(projcs);

            } else {
                Element geogcs = new Element("geogCoordSys");
                String sysname= tokens.nextToken();
                horizsys.setAttribute("name",sysname);
                geogcs.setAttribute("name",sysname);
                if (tokens.nextToken().equalsIgnoreCase("DATUM")){
                    geogcs.addContent(new Element("datum").setAttribute("name",tokens.nextToken()));
                } else {
                    System.out.print("Didnt find DATUM\n");
                }

                if (tokens.nextToken().equalsIgnoreCase("SPHEROID")){
                    Element spheroid = new Element("spheroid");
                    spheroid.setAttribute("name",tokens.nextToken());
                    spheroid.setAttribute("semiAxisMajor",tokens.nextToken());
                    spheroid.setAttribute("denomFlatRatio",tokens.nextToken());
                    geogcs.addContent(spheroid);
                } else {
                    System.out.print("Didnt find SPHEROID\n");
                }
                if (tokens.nextToken().equalsIgnoreCase("PRIMEM")){
                    Element primem=new Element("primeMeridian");
                    primem.setAttribute("name",tokens.nextToken());
                    primem.setAttribute("longitude",tokens.nextToken());
                    geogcs.addContent(primem);
                } else {
                    System.out.print("Didnt find PRIMEM\n");

                }
                if (tokens.nextToken().equalsIgnoreCase("UNIT")){
                    Element unit=new Element("unit");
                    unit.setAttribute("name",tokens.nextToken());
                    unit.setAttribute("radiansPerUnit",tokens.nextToken());
                    geogcs.addContent(unit);
                } else {
                    System.out.print("Didnt find UNIT\n");

                }

                horizsys.addContent(geogcs);

            }
            root.addContent(horizsys);

        }catch (Exception e) {
            System.out.print(e);
        }




    }




}



