package vn.oop.Homework04;

public class Main {

    public static void main(String[] args) {

        System.out.println("Tạo tài khoản ngân hàng");
        BankAccount Hoang = new BankAccount("1", "Hoang", 200000);
        BankAccount Do   = new BankAccount("2", "Do",   100000);


        System.out.println("Nạp tiền");
        Hoang.deposit(50000);
        Do.deposit(-1000);


        System.out.println("Rút tiền");
        Hoang.withdraw(100000);
        Do.withdraw(90000);


        System.out.println("Chuyển tiền");
        Hoang.transfer(Do, 50000);
        Do.transfer(Hoang, 90000);

        System.out.println("Chuyển tiền thất bại, ko đủ số dư");
        Hoang.transfer(Do, 500000);


        System.out.println("Thanh toán hóa đơn");
        Hoang.payBill("Điện", 20000);
        Hoang.payBill("Nước", 5000000);


        System.out.println("Số dư cuối cùng");
        System.out.println("[" + Hoang.getAccountNumber() + "] " + Hoang.getOwnerName()
                + " - Số dư: " + Hoang.getBalance());
        System.out.println("[" + Do.getAccountNumber() + "] " + Do.getOwnerName()
                + " - Số dư: " + Do.getBalance());
    }
}
