
module SAOITR {
	requires com.google.gson;
	requires jbcrypt;
	requires java.desktop;
	requires java.sql;
	opens client to com.google.gson;
}