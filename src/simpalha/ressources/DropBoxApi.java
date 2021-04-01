/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpalha.ressources;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cyrin
 */
public class DropBoxApi {

    private static final String ACCESS_TOKEN = "LCgUqFLr-TQAAAAAAAAAAR0gPpN_syqUBkLEZAnzhU832_i8_USGg3RpNdh8hIfX";

    public void uploadDropBox(String path) throws FileNotFoundException, IOException {
        try {
            // Create Dropbox client

            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/Applications/Save_Resource").build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
            // Get current account info
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());

            // Upload "test.txt" to Dropbox
            try (InputStream in = new FileInputStream(path)) {
                FileMetadata metadata = client.files().uploadBuilder("/test.pdf")
                        .uploadAndFinish(in);
                
//                 try (InputStream in = new FileInputStream(path)) {
//                FileMetadata metadata = client.files().uploadBuilder("/"+R1.getTitle+".pdf")
//                        .uploadAndFinish(in);
            }
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
