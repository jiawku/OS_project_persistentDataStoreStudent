package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {


	@Override
	public void run() throws IOException, ServerException {
		//get file name and data size from client
		String Read_Name = StreamUtil.readLine(inputStream);
		String Data_Size = StreamUtil.readLine(inputStream);
		int Data_Length = Integer.parseInt(Data_Size);
		
		//write data from client to local file system
		byte[] Data = StreamUtil.readData(Data_Length, inputStream);
		FileUtil.writeData(Read_Name, Data);
		//send feed back
		this.sendOK();
	}
}
