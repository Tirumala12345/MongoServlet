package com.servlet;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private  String username;
    private  String password;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String uri ="mongodb://localhost:27017";
        MongoClientURI clientURI = new MongoClientURI(uri);
        mongoClient = new MongoClient(clientURI);
        database = mongoClient.getDatabase("crm");

        MongoCollection<Document> collection = database.getCollection("user");


        username = req.getParameter("username");
        password = req.getParameter("password");
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            out.println("<h1>Username and Password are required!</h1>");
            return;
        }

        Document user = new Document("username", username)
                .append("password", password);

        collection.insertOne(user);

        out.println("<h1>Registration Successful</h1>");
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mongoClient != null) {
            mongoClient.close();
        }


    }
}
