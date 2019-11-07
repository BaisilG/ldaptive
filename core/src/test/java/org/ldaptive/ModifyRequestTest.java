/* See LICENSE for licensing and NOTICE for copyright. */
package org.ldaptive;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link ModifyRequest}.
 *
 * @author  Middleware Services
 */
public class ModifyRequestTest
{


  /**
   * Modify test data.
   *
   * @return  request test data
   */
  @DataProvider(name = "request")
  public Object[][] createData()
  {
    return
      new Object[][] {
        new Object[] {
          ModifyRequest.builder()
            .dn("uid=jdoe,ou=People,dc=example,dc=com")
            .modificiations(
              new AttributeModification(AttributeModification.Type.DELETE, new LdapAttribute("givenName", "John")),
              new AttributeModification(AttributeModification.Type.ADD, new LdapAttribute("givenName", "Jonathan")),
              new AttributeModification(AttributeModification.Type.REPLACE, new LdapAttribute("cn", "Jonathan Doe")))
            .build(),
          new byte[] {
            // preamble
            0x30, (byte) 0x84, 0x00, 0x00, 0x00, (byte) 0x80, 0x02, 0x01, 0x02,
            // modify op
            0x66, 0x7b,
            // entry DN
            0x04, 0x24, 0x75, 0x69, 0x64, 0x3d, 0x6a, 0x64, 0x6f, 0x65, 0x2c, 0x6f, 0x75, 0x3d, 0x50, 0x65, 0x6f, 0x70,
            0x6c, 0x65, 0x2c, 0x64, 0x63, 0x3d, 0x65, 0x78, 0x61, 0x6d, 0x70, 0x6c, 0x65, 0x2c, 0x64, 0x63, 0x3d, 0x63,
            0x6f, 0x6d,
            // modifications
            0x30, 0x53,
            // delete attribute modification
            0x30, 0x18, 0x0a, 0x01, 0x01, 0x30, 0x13, 0x04, 0x09, 0x67, 0x69, 0x76, 0x65, 0x6e, 0x4e, 0x61, 0x6d, 0x65,
            0x31, 0x06, 0x04, 0x04, 0x4a, 0x6f, 0x68, 0x6e,
            // add attribute modification
            0x30, 0x1c, 0x0a, 0x01, 0x00, 0x30, 0x17, 0x04, 0x09, 0x67, 0x69, 0x76, 0x65, 0x6e, 0x4e, 0x61, 0x6d, 0x65,
            0x31, 0x0a, 0x04, 0x08, 0x4a, 0x6f, 0x6e, 0x61, 0x74, 0x68, 0x61, 0x6e,
            // replace attribute modification
            0x30, 0x19, 0x0a, 0x01, 0x02, 0x30, 0x14, 0x04, 0x02, 0x63, 0x6e, 0x31, 0x0e, 0x04, 0x0c, 0x4a, 0x6f, 0x6e,
            0x61, 0x74, 0x68, 0x61, 0x6e, 0x20, 0x44, 0x6f, 0x65},
        },
        new Object[] {
          ModifyRequest.builder()
            .dn("uid=1,ou=test,dc=vt,dc=edu")
            .modificiations(new AttributeModification(AttributeModification.Type.DELETE, new LdapAttribute("authzTo")))
            .build(),
          new byte[] {
            // preamble
            0x30, 0x35, 0x02, 0x01, 0x02,
            // modify op
            0x66, 0x30,
            // entry DN
            0x04, 0x1A, 0x75, 0x69, 0x64, 0x3D, 0x31, 0x2C, 0x6F, 0x75, 0x3D, 0x74, 0x65, 0x73, 0x74, 0x2C, 0x64, 0x63,
            0x3D, 0x76, 0x74, 0x2C, 0x64, 0x63, 0x3D, 0x65, 0x64, 0x75,
            // modifications
            0x30, 0x12,
            // delete attribute modification
            0x30, 0x10, 0x0A, 0x01, 0x01, 0x30, 0x0B, 0x04, 0x07, 0x61, 0x75, 0x74, 0x68, 0x7A, 0x54, 0x6F, 0x31, 0x00},
        },
      };
  }


  /**
   * @param  request  modify request to encode.
   * @param  berValue  expected value.
   *
   * @throws  Exception  On test failure.
   */
  @Test(dataProvider = "request")
  public void encode(final ModifyRequest request, final byte[] berValue)
    throws Exception
  {
    Assert.assertEquals(request.encode(2), berValue);
  }
}
