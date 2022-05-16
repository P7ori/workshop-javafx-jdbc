module workshop_javafx_jdbc
{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens application;
	opens gui;
	opens model.entities;
	opens model.services;
	opens model.dao;
	opens model.dao.impl;
	opens db;
}