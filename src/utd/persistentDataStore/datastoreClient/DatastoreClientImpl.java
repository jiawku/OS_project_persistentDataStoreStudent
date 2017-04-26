package utd.persistentDataStore.datastoreClient;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;
import utd.persistentDataStore.datastoreClient.ClientException;
import utd.persistentDataStore.datastoreClient.ConnectionException;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException, ConnectionException
	{
		logger.debug("Executing Write Operation");
		String feedback;
		
		try{

			Socket clientSoc = new Socket(address, port); 		//connection built
			InputStream clientSocInput = clientSoc.getInputStream();		//set input and output Stream
			OutputStream clientSocOutput  = clientSoc.getOutputStream();

			
			StreamUtil.writeLine("write", clientSocOutput);
			StreamUtil.writeLine(name, clientSocOutput);
			StreamUtil.writeLine(String.valueOf(data.length), clientSocOutput);
			StreamUtil.writeData(data, clientSocOutput);
			
			feedback=StreamUtil.readLine(clientSocInput);
			if (!feedback.equalsIgnoreCase("ok")){
				clientSocInput.close();
				clientSocOutput.close();
				clientSoc.close();
				throw new ClientException(feedback);
			} else {
				logger.debug("Write success");
			}
			
			clientSocInput.close();
			clientSocOutput.close();
			clientSoc.close();
			
		} catch (Exception e){
			throw new ClientException("Write Error",e);
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException, ConnectionException
	{
		logger.debug("Executing Read Operation");
		String feedback;
		byte[] data = null;
		try{

			Socket clientSoc = new Socket(address, port); 		//connection built
			InputStream clientSocInput = clientSoc.getInputStream();		//set input and output Stream
			OutputStream clientSocOutput  = clientSoc.getOutputStream();

			
			StreamUtil.writeLine("read", clientSocOutput);
			StreamUtil.writeLine(name, clientSocOutput);

			
			feedback=StreamUtil.readLine(clientSocInput);
			if (!feedback.equalsIgnoreCase("ok")){				
				clientSocInput.close();
				clientSocOutput.close();
				clientSoc.close();
				throw new ClientException(feedback);
			} else {
				logger.debug("Read success");
				int datalength = Integer.parseInt(StreamUtil.readLine(clientSocInput));
				data = StreamUtil.readData(datalength, clientSocInput);
								
			}
			
			clientSocInput.close();
			clientSocOutput.close();
			clientSoc.close();
			
		} catch (Exception e){
			throw new ClientException("Read Error: No file name "+ name + ":\n",e);
		}
		return data;
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException, ConnectionException
	{
		logger.debug("Executing Delete Operation");
		String feedback;
		
		try{

			Socket clientSoc = new Socket(address, port); 		//connection built
			InputStream clientSocInput = clientSoc.getInputStream();		//set input and output Stream
			OutputStream clientSocOutput  = clientSoc.getOutputStream();

			
			StreamUtil.writeLine("delete", clientSocOutput);
			StreamUtil.writeLine(name, clientSocOutput);

			
			feedback=StreamUtil.readLine(clientSocInput);
			if (!feedback.equalsIgnoreCase("ok")){				
				clientSocInput.close();
				clientSocOutput.close();
				clientSoc.close();
				throw new ClientException( feedback);
			} else {
				logger.debug("delete success");								
			}
			
			clientSocInput.close();
			clientSocOutput.close();
			clientSoc.close();
			
		} catch (Exception e){
			throw new ClientException("Delete Error: No file name "+ name + ":\n",e);
		}
		
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
    public List<String> directory() throws ClientException, ConnectionException
	{
		logger.debug("Executing Directory Operation");
		List<String> filelist = new ArrayList<>();
		String feedback;
		
		try{

			Socket clientSoc = new Socket(address, port); 		//connection built
			InputStream clientSocInput = clientSoc.getInputStream();		//set input and output Stream
			OutputStream clientSocOutput  = clientSoc.getOutputStream();

			
			StreamUtil.writeLine("directory", clientSocOutput);
			
			feedback=StreamUtil.readLine(clientSocInput);
			if (!feedback.equalsIgnoreCase("ok")){				
				clientSocInput.close();
				clientSocOutput.close();
				clientSoc.close();
				throw new ClientException(feedback);
			} else {
				logger.debug("directory success");		
				int datalength = Integer.parseInt(StreamUtil.readLine(clientSocInput));
				for(int i=0;i<datalength;i++){
					filelist.add(StreamUtil.readLine(clientSocInput));
				}
			}
			
			clientSocInput.close();
			clientSocOutput.close();
			clientSoc.close();
			
		} catch (Exception e){
			throw new ClientException("directory Error ",e);
		}
		
		
		return filelist;
	}

}
