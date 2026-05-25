//package com.example;
//
//import com.example.client.DataService;
//import com.example.client.SoapDataService;
//
//import java.util.List;
//
//public class SoapClient {
//    public static void main(String[] args) {
//        String studentCode = "B22DCCN692";
//        String qCode = "2Qw5FBSH";
//
//        // Tạo service từ WSDL
//        DataService service = new DataService();
//
//        // Lấy port (proxy) để gọi các hàm
//        SoapDataService port = service.getSoapDataServicePort();
//
//        // Gọi getData để lấy danh sách số nguyên
//        List<Integer> numbers = port.getData(studentCode, qCode);
//        System.out.println("Server trả về: " + numbers);
//
//        // Tính tổng
//        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
//        System.out.println("Tổng = " + sum);
//
//        // Gửi kết quả tổng về server
//        port.submitDataInt(studentCode, qCode, sum);
//        System.out.println("Đã gửi kết quả về server.");
//    }
//}
