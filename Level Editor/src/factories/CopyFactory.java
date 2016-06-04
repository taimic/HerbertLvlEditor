package factories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CopyFactory {
	/**
	 * Creates a deep copy of the given object.
	 * @param object The object to copy
	 * @param type The type of the object
	 * @return The copied object
	 * @throws Exception
	 */
	public static <T> T copy(T object, Class<T> type) throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
		ObjectInputStream inStream = null;

		try {
			// serialize and write obj2DeepCopy to byteOut
			outStream.writeObject(object);
			// always flush your stream
			outStream.flush();

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			inStream = new ObjectInputStream(byteIn);

			// read the serialized, and deep copied, object and return it
			return type.cast(inStream.readObject());
		} catch (Exception e) {
			throw (e);
		} finally {
			outStream.close();
			inStream.close();
		}
	}

}
