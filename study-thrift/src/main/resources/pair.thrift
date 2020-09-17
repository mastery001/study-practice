namespace java service.demo

struct Pair {
    1:required string key,
    2:required string value
}


//private String datafile = "1.dat";
//     
//// *) 把对象写入文件
//public void writeData() throws IOException, TException {
//    Pair pair = new Pair();
//    pair.setKey("rowkey").setValue("column-family");
// 
//    FileOutputStream fos = new FileOutputStream(new File(datafile));
//    pair.write(new TBinaryProtocol(new TIOStreamTransport(fos)));
//    fos.close();
//}　
//
//// *) 从文件恢复对象
//public void readData() throws TException, IOException {
//　　FileInputStream fis = new FileInputStream(new File(datafile));
// 
//　　Pair pair = new Pair();
//　　pair.read(new TBinaryProtocol(new TIOStreamTransport(fis)));
// 
//　　System.out.println("key => " + pair.getKey());
//　　System.out.println("value => " + pair.getValue());
// 
//　　fis.close();
//}
