pragma solidity ^0.4.2;

contract SupplyChain {
    address car = 0xca35b7d915458ef540ade6068dfe2f44e8fa733c; //汽车公司的地址
    address tyre = 0x14723a09acff6d2a60dcdf7aa4aff308fddc160c; //轮胎公司的地址
    address hub = 0x4b0897b0513fdc7c541b6d9d7e929c4e5364d2db; //轮毂公司的地址
    address alui = 0x583031d1113ad414f02576bd6afabfb302140225; //铝锭公司地址
    address alum = 0xdd870fa1b7c4700f2bd7f44238821c26f7392148; //铝矿公司的地址
    address bank = 0x95f452956282a5bfc2c8522bf23cd5525fcbcb9b; //银行地址

    mapping(address => Company) public companyMap; //地址与公司的映射
    mapping(address => Bill) public billMap; // 公司与单据的映射
    
    struct Bill{
        address payer; //付款人地址
        address payee; //收款人地址
        uint transactionCounts; //交易次数
        uint paymentTime; //支付时间
        uint repaymentTime; //还款时间
        uint billBalance; //余额
        bool ability; //是否具有还款能力
    }
    
    struct Company{
        bytes32 companyName; //公司名
        uint property; //公司资产
    }
    
    Bill public bill__ = Bill({payer : 0x0, payee : 0x0, transactionCounts : 0, paymentTime : 20000101, repaymentTime : 20000101, billBalance : 0, ability : false});
    
    Company public com__ = Company({companyName : "", property : 3000});
    constructor() public{
        // 建立公司
        Company memory car_com = com__;
        car_com.companyName = "Car";
        car_com.property = 10000;
        Company memory tyre_com = com__;
        tyre_com.companyName = "Tyre";
        Company memory hub_com = com__;
        hub_com.companyName = "Hub";
        Company memory alui_com = com__;
        alui_com.companyName = "Alui";
        Company memory alum_com = com__;
        alum_com.companyName = "Alum";
        Company memory bank_com = com__;
        bank_com.companyName = "Bank";
        bank_com.property = 100000;

        // 建立公司地址与公司的映射
        companyMap[car] = car_com;
        companyMap[tyre]= tyre_com;
        companyMap[hub] = hub_com;

        // 建立公司和bill的映射
        billMap[car] = bill__;
        billMap[tyre] = bill__;
        billMap[hub] = bill__;

    }
       

    // 创建单据事件
    event Bill_(string msg, address payer, address payee, uint amount, bool ability);
    // 单据转让事件
    event Tran_(string msg, address transferer, address receiver, uint amount, uint payerBillBalance, uint payeeBillBalance, bool payerBillAbility, bool payeeBillAbility);
    // 融资事件
    event Fina_(string msg, address company, address financier, uint amount, uint companyPro);
    // 还款事件
    event Pay_(string msg, address payer, address payee, uint amount, uint payerPro, uint payeePro);
    // 创建单据
    function createBill(address payee, uint amount) public {
        require(msg.sender != payee, "Bill payee cannot be yourself!");
        require(payee != bank, "Bill payee cannot be bank!");
        Bill memory bill;
        bill.transactionCounts = 1;
        bill.payer = msg.sender;
        if (bill.payer != car) {
            bill.ability = false;
        }
        else bill.ability = true;
        bill.payee = payee;
        bill.billBalance = amount;
        // 创建公司的Bill
        billMap[msg.sender] = bill;
        billMap[payee] = bill;
        emit Bill_("Create a bill!", bill.payer, payee, amount, bill.ability);
    }
    // 转让单据
    function transferBill(address receiver, uint amount) public{
        require(billMap[msg.sender].billBalance >= amount, "Insufficient bill balance!");
        billMap[msg.sender].payer = msg.sender;
        billMap[msg.sender].payee = receiver;
        billMap[msg.sender].billBalance -= amount;
        billMap[msg.sender].transactionCounts += 1;
        billMap[receiver].transactionCounts += 1;
        billMap[receiver].payer = msg.sender;
        billMap[receiver].payee = receiver;
        billMap[receiver].billBalance += amount;
        billMap[receiver].ability = billMap[msg.sender].ability;
        emit Tran_("Transfer a Bill!", msg.sender, receiver, amount, billMap[msg.sender].billBalance, billMap[receiver].billBalance, billMap[msg.sender].ability, billMap[receiver].ability);
        
    }
    // 向银行申请融资
    function applyFinancing(address financier, uint amount) public{
        require(financier == bank, "Please apply to the bank for financing");
        require(billMap[msg.sender].ability == true, "Bills are not eligible for financing!");
        require(billMap[msg.sender].billBalance >= amount, "The amount of financing exceeds the balance of the bill!");
        companyMap[msg.sender].property += amount;
        billMap[msg.sender].payer = msg.sender;
        billMap[msg.sender].payee = bank;
        billMap[msg.sender].transactionCounts += 1;
        billMap[msg.sender].billBalance -= amount;
        emit Fina_("Apply to the bank for financing!", msg.sender, financier, amount, companyMap[msg.sender].property);
    }
    // 还款
    function payBill(address payee) public {
        require(msg.sender == car, "You don't have to pay bill!");
        require(billMap[payee].ability == true && billMap[payee].billBalance > 0, "You don't have to pay no ability bill!");
        require(companyMap[msg.sender].property > billMap[payee].billBalance, "Insufficient company assets!"); 
        uint amount = billMap[payee].billBalance;
        billMap[payee] = bill__;
        companyMap[msg.sender].property -= amount;
        companyMap[payee].property += amount;
        emit Pay_("Pay a bill!", msg.sender, payee, amount, companyMap[msg.sender].property, companyMap[payee].property);
        
    }
}