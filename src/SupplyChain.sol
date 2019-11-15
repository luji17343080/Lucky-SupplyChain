pragma solidity ^0.4.2;

contract SupplyChain {
    enum Actor{ Others, Producer, Retailer, Customer}
    enum CompanyType{ Car, Tyre, Hub, Aluingot, Aluminum, Bank }
    enum ProductType{ tyre, hub, aluingot, aluminum, money }
    enum CommodityType{ Wine, Jewelry, Shrimp, Others}
    enum Phase{ Supplier, Producer, Dealer, Retailer, Customer}

    struct Bill{
        address payer; //付款人地址
        address payee; //收款人地址
        uint transactionCounts; //交易次数
        uint paymentTime; //支付时间
        uint repaymentTime; //还款时间
        uint billBalance; //余额
        bytes32 tradeInf; //交易信息
        bool ability; //是否具有还款能力
    }

    struct Company{
        bytes32 companyName; //公司名
        uint property; //公司资产
        Bill bill_; //该公司持有的单据
    }

    
    mapping(address => Company) companyMap; //地址与公司的映射
    
    mapping(address => bytes32) nameMap; //地址与公司名的映射
    address car = 0x0192a157a51f2c7f130c854b2c6d8247e98bc9a0; //汽车公司的地址
    address tyre = 0xa778ec346d58da0f43e5c391713f582144ea2f15; //轮胎公司的地址
    address hub = 0xaab8dec515af916851ce21d47128e7037846352f; //轮毂公司的地址
    address alui = 0x31c76adfe827253ed48aad024f73fada89c03b77; //
    address alum = 0x989ffc0b75466f8b31127852266cc0eb28d40954; //铝矿公司的地址
    event Bill_(address payer, address payee, uint value, bool ability);
    function createBill(address payee, uint amount) public {
        Bill storage bill;
        bill.transactionCounts = 1;
        bill.payer = msg.sender;
        if (bill.payer != 0x0192a157a51f2c7f130c854b2c6d8247e98bc9a0) {
            bill.ability = true;
        } 
        bill.ability = false;
        bill.payee = payee;
        bill.billBalance = amount;
        emit Bill_(bill.payer, payee, amount, bill.ability);
    }

    function transferBill(address payee, uint amount) public{

    }
}