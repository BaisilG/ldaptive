/* See LICENSE for licensing and NOTICE for copyright. */
package org.ldaptive.protocol;

import org.ldaptive.asn1.DefaultDERBuffer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link SearchResultReference}.
 *
 * @author  Middleware Services
 */
public class SearchResultReferenceTest
{


  /**
   * Search result reference test data.
   *
   * @return  response test data
   */
  @DataProvider(name = "response")
  public Object[][] createData()
  {
    return
      new Object[][] {
        new Object[] {
          // reference with two URLs
          new byte[] {
            //preamble
            0x30, 0x6d, 0x02, 0x01, 0x02,
            // search result reference
            0x73, 0x68,
            // URL 1
            0x04, 0x32, 0x6c, 0x64, 0x61, 0x70, 0x3a, 0x2f, 0x2f, 0x64, 0x73, 0x31, 0x2e, 0x65, 0x78, 0x61, 0x6d, 0x70,
            0x6c, 0x65, 0x2e, 0x63, 0x6f, 0x6d, 0x3a, 0x33, 0x38, 0x39, 0x2f, 0x64, 0x63, 0x3d, 0x65, 0x78, 0x61, 0x6d,
            0x70, 0x6c, 0x65, 0x2c, 0x64, 0x63, 0x3d, 0x63, 0x6f, 0x6d, 0x3f, 0x3f, 0x73, 0x75, 0x62, 0x3f,
            // URL 2
            0x04, 0x32, 0x6c, 0x64, 0x61, 0x70, 0x3a, 0x2f, 0x2f, 0x64, 0x73, 0x32, 0x2e, 0x65, 0x78, 0x61, 0x6d, 0x70,
            0x6c, 0x65, 0x2e, 0x63, 0x6f, 0x6d, 0x3a, 0x33, 0x38, 0x39, 0x2f, 0x64, 0x63, 0x3d, 0x65, 0x78, 0x61, 0x6d,
            0x70, 0x6c, 0x65, 0x2c, 0x64, 0x63, 0x3d, 0x63, 0x6f, 0x6d, 0x3f, 0x3f, 0x73, 0x75, 0x62, 0x3f},
          SearchResultReference.builder()
            .messageID(2)
            .uris(
              "ldap://ds1.example.com:389/dc=example,dc=com??sub?",
              "ldap://ds2.example.com:389/dc=example,dc=com??sub?").build(),
        },
      };
  }

  /**
   * @param  berValue  encoded response.
   * @param  response  expected decoded response.
   *
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"provider"}, dataProvider = "response")
  public void encode(final byte[] berValue, final SearchResultReference response)
    throws Exception
  {
    Assert.assertEquals(new SearchResultReference(new DefaultDERBuffer(berValue)), response);
  }
}
