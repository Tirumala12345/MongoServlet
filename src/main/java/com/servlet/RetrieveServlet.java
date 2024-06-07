package com.servlet;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RetrieveServlet extends HttpServlet {
    private MongoClient mongoClient; //MongoClient is used to making connections to MongoDB
    private MongoDatabase database;

    public void init() throws ServletException{
        String url="mongodb://localhost:27017";
        MongoClientURI clientURI=new MongoClientURI(url);
        mongoClient=new MongoClient(clientURI);
        database= mongoClient.getDatabase("crm");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
        PrintWriter out=response.getWriter();
        response.setContentType("text/html");

        MongoCollection<Document> collection= database.getCollection("user");
        FindIterable<Document> documents=collection.find();

        out.println("<h1>User List</h1>");
        out.println("<ul");

        for(Document document:documents){
            String username=document.getString("username");
            String password=document.getString("password");
            out.println("<li>Username: " +username + ", Password: " +password +"</li>");
        }
        out.println("</ul>");
    }

    public void destroy(){
        super.destroy();
        if(mongoClient!=null){
            mongoClient.close();
        }
    }
}
