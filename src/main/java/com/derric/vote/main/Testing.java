package com.derric.vote.main;

import java.io.IOException;
import java.util.Base64;

import com.datastax.driver.core.utils.Bytes;

public class Testing {

	public static void main(String[] args) throws IOException {
/*		Cluster cluster=Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session=cluster.connect("vote");
		
		File image=new File("E://tensorflow-for-poets-2//tf_files//flower_photos//daisy//174131220_c853df1287.jpg");
		BufferedImage originalImage=ImageIO.read(image);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ImageIO.write(originalImage,"jpg", baos);
		byte[] imageInByte=baos.toByteArray();
		for(byte b:imageInByte) {
			System.out.print(b);
		}
		System.out.println();
		baos.close();  
		System.out.println("Length first::::"+imageInByte.length);
		System.out.println("simplify"); 
		byte[] imgby=Files.readAllBytes(image.toPath());
		for(int i=0;i<imgby.length;i++) {
			System.out.print(imgby[i]);
		}
		
		System.out.println("Length: "+imgby.length); 
		
		FileInputStream fis=new FileInputStream("E://tensorflow-for-poets-2//tf_files//flower_photos//daisy//174131220_c853df1287.jpg");
		byte[] array=new byte[fis.available()+1];
		int len=array.length;
		System.out.println("lenL"+len);
		fis.read(array);
		ByteBuffer buffer=ByteBuffer.wrap(array);
		System.out.println(buffer.toString());
		PreparedStatement ps=session.prepare("insert into candidate(candidate_id,party,profile_photo) values(?,?,?);");
		BoundStatement bs=ps.bind(UUID.randomUUID(),"BJP",array);
		session.execute(bs); */
		
		byte[] b="Hello".getBytes();
		for(int i=0;i<b.length;i++) {
			System.out.print(b[i]+" ");
		}
		System.out.println(Bytes.toHexString(b));
		String encoded=Base64.getEncoder().encodeToString(b);
		System.out.println(encoded);
}
}