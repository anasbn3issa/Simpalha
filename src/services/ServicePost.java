/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Comment;
import entities.Post;
import interfaces.IServicePost;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utils.Maconnexion;
/**
 *
 * @author anaso
 */
public class ServicePost implements IServicePost {

    Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ServicePost() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void Create(Post variable) {
        try {
            Statement st = cnx.createStatement();
            String query = "INSERT INTO post(module,owner_id, problem,image_name) VALUES ('" + variable.getModule() + "','" + variable.getOwnerId()+ "','" + variable.getProblem() + "','" + variable.getImageName() + "')";
            st.executeUpdate(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("enregistré");
            alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(Post variable) {
        String query = "update post set module=?,problem=? where id=?";
        System.out.println(variable.toString());
        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, variable.getModule());
            pst.setString(2, variable.getProblem());
            pst.setInt(3, variable.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Post> Read() {

        List<Post> list = new ArrayList<>();
        String req = "select * from post ORDER BY timestamp DESC";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setOwnerId(rs.getInt("owner_id"));
                p.setSolution_id(rs.getInt("solution_id"));
                p.setProblem(rs.getString("problem"));
                p.setModule(rs.getString("module"));
                p.setTimestamp(rs.getTimestamp("timestamp"));
                p.setImageName(rs.getString("image_name"));
                list.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    public List<String> ReadModules(){
        List<String> list = new ArrayList<>();
        String req = "select * from module ORDER BY name ASC";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(rs.getString("name"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    @Override
    public void Delete(Post variable) {
        try {
            String requete;
            requete = "delete from post where id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, variable.getId());
            pst.executeUpdate();
            pst.executeUpdate(); 
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Post> findAllById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Post findById(int id) {
        String query = "select * from post where id=?";
        Post p = new Post();
        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                 p.setId(rs.getInt("id"));
                p.setTimestamp(rs.getTimestamp("timestamp"));
                p.setSolution_id(rs.getInt("solution_id"));
                p.setStatus(rs.getString("status"));
                p.setProblem(rs.getString("problem"));
                p.setModule(rs.getString("module"));
                p.setOwnerId(rs.getInt("owner_id"));
                p.setImageName(rs.getString("image_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;

    }

    @Override
    public List<Comment> findAllCommentsForThisPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        String query = "select * from comment where id_Post=? ORDER BY timestamp DESC";

        try {
            pst = cnx.prepareStatement(query);
            pst.setInt(1, postId);
            rs = pst.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setTimestamp(rs.getTimestamp("timestamp"));
                c.setId_Post(rs.getInt("id_Post"));
                c.setUpvotes(rs.getInt("upvotes"));
                c.setDownvotes(rs.getInt("downvotes"));
                c.setSolution(rs.getString("solution"));
                c.setOwnerId(rs.getInt("owner_id"));
                comments.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comments;
    }

   

    @Override
    public List<Post> findPostsByModule(String module) {
        List<Post> posts = new ArrayList<>();
        String query = "select * from post where module=? ORDER BY timestamp DESC";

        try {
            pst = cnx.prepareStatement(query);
            pst.setString(1, module);
            rs = pst.executeQuery();
            while (rs.next()) {
                Post c = new Post();
                c.setId(rs.getInt("id"));
                c.setProblem(rs.getString("problem"));
                c.setTimestamp(rs.getTimestamp("timestamp"));
                c.setStatus(rs.getString("status"));
                c.setModule(rs.getString("module"));
                c.setImageName(rs.getString("image_name"));
                c.setOwnerId(rs.getInt("owner_id"));
                posts.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE, null, ex);
        }

        return posts;
        
        
        }

    @Override
    public String xTimeAgo(Timestamp timestamp) {
         long current = System.currentTimeMillis();
        
        
        
        return null;
    }

    
    private void ExportExcel(ActionEvent event) throws FileNotFoundException, IOException {
         try {        
             String query = "Select * from post";
             pst = cnx.prepareStatement(query);
             rs = pst.executeQuery();
            //Variable counter for keeping track of number of rows inserted.  
            int counter = 1;
            FileOutputStream fileOut = null;
           
            

            //Creation of New Work Book in Excel and sheet.  
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            //Creating Headings in Excel sheet.  
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 1).setCellValue("timestamp");//Row Name1  
            rowhead.createCell((short) 2).setCellValue("status");//Row Name2  
            rowhead.createCell((short) 3).setCellValue("module");//Row Name3  
            rowhead.createCell((short) 4).setCellValue("problem");//Row Name4
            rowhead.createCell((short) 5).setCellValue("owner_id");//Row Name4
            
            

            
            while (rs.next()) {
                //Insertion in corresponding row  
                HSSFRow row = sheet.createRow((int) counter);
        
                row.createCell((short) 1).setCellValue(rs.getInt("timestamp"));
                row.createCell((short) 2).setCellValue(rs.getString("status"));
                row.createCell((short) 3).setCellValue(rs.getString("module"));
                row.createCell((short) 4).setCellValue(rs.getString("problem"));
                row.createCell((short) 5).setCellValue(rs.getString("owner_id"));
               
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256 * 25);

                sheet.setZoom(150);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256 * 25);

                sheet.setZoom(150);

                counter++;
                try {
                    //For performing write to Excel file  
                    fileOut = new FileOutputStream("Posts.xls");
                    hwb.write(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Close all the parameters once writing to excel is compelte.  
            fileOut.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION DIALOG");
            alert.setHeaderText(null);
            alert.setContentText("All Posts Has Been Exported in Excel Sheet");
            alert.showAndWait();
            rs.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
