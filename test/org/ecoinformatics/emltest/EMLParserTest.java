/**
 *  '$RCSfile: EMLParserTest.java,v $'
 *  Copyright: 2000 Regents of the University of California and the
 *              National Center for Ecological Analysis and Synthesis
 *    Authors: @authors@
 *    Release: @release@
 *
 *   '$Author: berkley $'
 *     '$Date: 2002-09-30 19:54:09 $'
 * '$Revision: 1.4 $'
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.ecoinformatics.emltest;

import org.ecoinformatics.eml.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * A JUnit test for testing the EMLParser
 *
 * @author   Chad Berkley
 */
public class EMLParserTest extends TestCase
{
  private final static String VALID = "test/eml.xml";
  private final static String ERROR1 = "test/eml-error1.xml";
  private final static String ERROR2 = "test/eml-error2.xml";
  private final static String ERROR3 = "test/eml-error3.xml";
  private final static String ERROR4 = "test/eml-error4.xml";

  private EMLParser emlp;

  /**
   * Constructor to build the test
   *
   * @param name  the name of the test method
   */
  public EMLParserTest(String name)
  {
    super(name);
  }

  /** Establish a testing framework by initializing appropriate objects  */
  public void setUp()
  {

  }

  /** Release any objects after tests are complete  */
  public void tearDown()
  {
  }

  /**
   * Create a suite of tests to be run together
   *
   * @return   The test suite
   */
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    suite.addTest(new EMLParserTest("initialize"));
    suite.addTest(new EMLParserTest("testParse"));
    return suite;
  }

  /**
   * Check that the testing framework is functioning properly with a trivial
   * assertion.
   */
  public void initialize()
  {
    assertTrue(true);
  }

  public void testParse()
  {
    try
    {
      File f = new File(VALID);
      emlp = new EMLParser(f);
    }
    catch(Exception e)
    {
      fail("Error.  This file should have parsed correctly: " + e.getMessage());
    }

    try
    {
      File f = new File(ERROR1);
      emlp = new EMLParser(f);
      fail("Error 1. An EMLParserException should have been thrown.");
    }
    catch(Exception e)
    {
      //System.out.println(e.getMessage());
      assertTrue(e.getMessage().indexOf("Error in xml document.  " +
        "This EML document is not valid because the id 23445 occurs more " +
        "than once.  IDs must be unique.") != -1);
    }

    try
    {
      File f = new File(ERROR3);
      emlp = new EMLParser(f);
      fail("Error 3. An EMLParserException should have been thrown.");
    }
    catch(Exception e)
    {
      //System.out.println(e.getMessage());
      assertTrue(e.getMessage().equals("Error processing keyrefs: " +
        "//references : Error in xml document. This EML instance is " +
        "invalid because referenced id 23447 does not exist in the " +
        "given keys."));
    }

    try
    {
      File f = new File(ERROR4);
      emlp = new EMLParser(f);
      fail("Error 3. An EMLParserException should have been thrown.");
    }
    catch(Exception e)
    {
      //System.out.println(e.getMessage());
      assertTrue(e.getMessage().equals("Error processing keyrefs: " +
        "//references : Error in xml document. This EML instance is invalid " +
        "because this element has an id and it is being used in " +
        "a keyref expression."));
    }
  }
}

