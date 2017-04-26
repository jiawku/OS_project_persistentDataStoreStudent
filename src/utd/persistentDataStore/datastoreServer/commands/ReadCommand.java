package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;


public class ReadCommand extends ServerCommand{
	
	@Override
	public void run() throws IOException, ServerException {

		String Read_Name = StreamUtil.readLine(inputStream);
		byte[] Data = null;
		Data = FileUtil.readData(Read_Name);
		int Data_Length = Data.length;
		if (Data_Length != 0){
			this.sendOK();
			String Length = Data_Length + "\n";
			StreamUtil.writeLine(Length, outputStream);
			StreamUtil.writeData(Data, outputStream);
		}
		else{
			this.sendError("Wrong file Name");
			throw new ServerException("Fail to read");
		}
	}

}
