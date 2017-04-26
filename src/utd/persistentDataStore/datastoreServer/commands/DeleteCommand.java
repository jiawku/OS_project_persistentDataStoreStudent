package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand   {

	@Override
	public void run() throws IOException, ServerException {

		String Read_Name = StreamUtil.readLine(inputStream);
		if (FileUtil.deleteData(Read_Name)){
			this.sendOK();
		}
		else{
			this.sendError("Unkonwn file");
			throw new ServerException("File deletion failed");
		}
	}
}