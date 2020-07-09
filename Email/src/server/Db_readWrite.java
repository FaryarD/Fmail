package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Db_readWrite {
	private static final String data_path="data/server.db";
	private Db_Clients db;
	public Db_readWrite() {
		db=new Db_Clients();
		File file=new File(data_path);
		if(file.exists()) {
			db=loadDb();
		}
		else {
			file.getParentFile().mkdirs();
		}
		if(db==null) {
			file.getParentFile().mkdirs();
			db=new Db_Clients();
		}
	}
	public Db_Clients getDb() {
		return db;
	}
	public boolean saveDb() {
		boolean res=false;
		try {
			FileOutputStream file=new FileOutputStream(data_path);
			
			ObjectOutputStream obj_straem=new ObjectOutputStream(file);
			obj_straem.writeObject(db);
			obj_straem.close();
			res=true;
			
		} catch (FileNotFoundException e) {//FileOutputStream
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		} catch (IOException e) {//ObjectOutputStream
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		return res;
	}
	public Db_Clients loadDb() {
		Object obj = null;
		try {
			FileInputStream file=new FileInputStream(data_path);
			ObjectInputStream obj_straem=new ObjectInputStream(file);
			obj=obj_straem.readObject();
			obj_straem.close();
			
		} catch (FileNotFoundException e) {//FileOutputStream
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		return (Db_Clients) obj;
	}
}
