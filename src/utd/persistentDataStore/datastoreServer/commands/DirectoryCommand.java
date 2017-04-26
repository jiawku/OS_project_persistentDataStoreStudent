package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DirectoryCommand extends ServerCommand{
	
	@Override
	public void run() throws IOException, ServerException {
		//read directory from local file system
		List<String> directory = new ArrayList<String>();
		directory = FileUtil.directory();
		
		//send feed back and data to client
		this.sendOK();
		
		int length = directory.size();
		StreamUtil.writeLine(length + "\n", outputStream);
		
		for(String s: directory){
			StreamUtil.writeLine(s + "\n", outputStream);
		}
		
		
	}

}
