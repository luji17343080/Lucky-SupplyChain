![](images/00.png)  
## [【Github地址】](https://github.com/luji17343080/Lucky-SupplyChain)
## 一、项目背景  
![](images/01.png)  

整个项目的目的是将核心公司（车企）的`还款能力`传递下去，使有核心公司的欠款单据的链上的公司可以通过单据向金融机构（银行）借款融资。然而金融机构只认可车企的还款能力，所以当某企业凭单据向金融机构借款时，金融机构需要对该单据的公司进行评估看是否具有还款能力，在本次的情境下，只需要知道单据的源头是否为车企就行了。显然这就是一个简单的溯源问题，只需要追溯单据最初的付款方是否为车企，所以只需要通过区块链的方式将每次交易的信息记录下来就行了。因此，我认为这次需要设计的供应链金融的平台的核心就是`账款单据`，我通过一个`ability`属性来最终表示一个公司是否具有向金融机构借款融资的能力，当然，`ability`在链上是可以传递的。  

## 二、方案设计 
【[源码地址](https://github.com/luji17343080/Lucky-SupplyChain/blob/master/src/SupplyChain.sol)】
### 0. 数据建立  
#### 数据结构
`公司`Company结构体属性：`公司名称`，`资产`  
`单据`Bill结构体属性：`单据金额`，`单据交易双方的地址`，`单据交易时间`，`单据的还款时间`（精确到`日`），该单据是否被银行认可（`ability`为`True`和`False`），单据的交易次数（`transactionCounts`） 
```java
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
```
#### event
```java
    // 创建单据事件
    event Bill_(string msg, address payer, address payee, uint amount, bool ability);
    // 单据转让事件
    event Tran_(string msg, address transferer, address receiver, uint amount, uint payerBillBalance, uint payeeBillBalance, bool payerBillAbility, bool payeeBillAbility);
    // 融资事件
    event Fina_(string msg, address company, address financier, uint amount, uint companyPro);
    // 还款事件
    event Pay_(string msg, address payer, address payee, uint amount, uint payerPro, uint payeePro);
```
#### 构造函数
- Company创建并初始化
```java
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
    }
```
- 映射表的建立  
```java
        // 建立公司地址与公司的映射
        companyMap[car] = car_com;
        companyMap[tyre]= tyre_com;
        companyMap[hub] = hub_com;

        // 建立公司和bill的映射
        billMap[car] = bill__;
        billMap[tyre] = bill__;
        billMap[hub] = bill__;
```
  
|公司名|地址|
|:---|:---|
|Car|0xca35b7d915458ef540ade6068dfe2f44e8fa733c|
|Tyre|0x14723a09acff6d2a60dcdf7aa4aff308fddc160c|
|Hub|0x4b0897b0513fdc7c541b6d9d7e929c4e5364d2db|
|Alui|0x583031d1113ad414f02576bd6afabfb302140225|
|Alum|0xdd870fa1b7c4700f2bd7f44238821c26f7392148|
|Bank|0x95f452956282a5bfc2c8522bf23cd5525fcbcb9b|  
      
### 下面为四个基本功能的实现
### 1. 交易单据生成并上链    
单据生成函数：function createBill(address payee, uint amount) public {}  
```java
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
```
要求：
- 收款公司不能为单据生成公司  
- 收款公司不能为银行  

功能：
- 设置交易双方和单据金额（根据传入参数）
- 交易次数（`transactionCounts`）变为1
- 设置交易时间
- 根据payer（付款公司）确认该单据是否有`ability`
- 根据payee（收款公司）确认该单据交易的商品（`commodity`）信息
- 将Bill加入Commpany与Bill的映射表中

### 2. 单据的转让  
  
函数：function transferBill(address payee, uint amount) public{}  
```java
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
```
要求：
- 单据中的交易次数至少为1
- 单据的金额不小于交易金额  

功能： 
- 创建新的Bill
- 更新交易双方信息
- 付款方单据金额减少
- 收款方单据金额增加
- 交易时间的修改
- 交易次数加1
- `ability`不变
- 将更新的Bill加入到billMap中  
`说明：单据的转让过程中，其实交易双方的信息修改和交易商品的更新是不重要的，重要的是ability的传递、单据金额的变化和交易时间的更新，ability和金额确定公司能否根据此单据向银行借一定资金`
### 3. 凭单据向银行借款融资  
函数：function borrowBill(address payee, uint amount) public{}  
```java
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
```
要求：
- Bill的`ability`为`true`
- Bill的金额不小于借款金额
- Bill的收款方必须为银行  
  
功能：  
- 单据转让功能
- 公司资金增加
- 金融机构资金减少
- 将更新的Bill加入到`billMap`中

### 4. 单据的支付结算  
函数：function payBill() public {}  
```java
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
```
要求：
- 结算公司为`Car`
- 还款单据的`ability`为`true`
- 结算公司的`财产（property）`大于单据的`金额（billBalance）`  

功能：
- 单据销毁（重新初始化）
- 持有单据的公司资金增加  
- 核心公司的资金减少  
## 三、链端功能测试（Remix）  
### 1、单据生成
- `Car`公司生成`ability`为`true`的单据(能向银行借款)  

    **调用createBill函数，当前账户为payer（Car），payee为传入的公司地址(Tyre)，amount为单据金额**  

    ![](images/test1.png)  

    **函数调用结果**  

    ![](images/test2.png)  

    **查询"billMap"中的Bill结果(decoded input 为key：公司地址；decoded output为value：Bill信息)**  

    ![](images/test3.png)  

- `Tyre`公司生成`ability`为`false`的单据  

    **调用createBill函数，当前账户为payer（Tyre），payee为传入的公司地址(Car)，amount为单据金额**  

    ![](images/test4.png)  

    **函数调用结果**  

    ![](images/test5.png)  

    **查询"billMap"中的Bill结果(decoded input 为key：公司地址；decoded output为value：Bill信息)**  

    ![](images/test6_.png)  
    
- 不能生成`payee`为自身的单据  

    **调用createBill函数，当前账户为payer（Car），payee为传入的公司地址，amount为单据金额**  

    ![](images/test7_.png)  

    **函数调用结果**  

    ![](images/test9.png)  

    
- 不能生成`收款公司`为`Bank`的单据  

    **调用createBill函数，当前账户为payer（Car），payee为传入的公司地址(Bank)，amount为单据金额**  

    ![](images/test10.png)  

    **函数调用结果**  
    
    ![](images/test11.png)  
    
### 2、单据转让  
- 先调用`billMap`查看原来的`Tyre`公司持有的单据情况（特别是`billBalance`和`ability`）  
  
    ![](images/test12.png)  
      
- 再调用`billMap`查看`Hub`公司的单据  
    
    ![](images/test13.png)  

- 调用`transferBill`函数，当前账户为payer（`Tyre`），payee为`Hub`，amount为单据金额  
   - 当payer的`bill余额`小于`amount`时结果为
   
        ![](images/test14.png)  
        ![](images/test142.png)  

   - 当payer的`bill余额`不小于`amount`时结果为  

        ![](images/test150.png)
        ![](images/test15.png)  

- 单据转让后的结果为：
   - `Tyre`公司的单据更新：`payer`和`payee`的地址更新，单据交易次数(`transactionCounts`)加1，`billBalance`减去转让的金额（5000- 3000），`ability`不变  
     
        ![](images/test16.png)  
    
   - `Hub`公司的单据更新：`payer`和`payee`的地址更新，单据交易次数(`transactionCounts`)加1，`billBalance`加上转让的金额（0 + 3000），`ability`为payer的`ability`

        ![](images/test17.png)  

### 3、向银行申请融资  
- 调用`applyFinancing`函数，`Hub`公司凭借单据（如上图）向银行借款（`ability`为true）(公司的初始`property`都为3000)
   - 借款金额超过`3000`（5000），结果如下：
        
        ![](images/test18.png)  
        
   - 借款金额为低于3000（`2000`），公司的`property`增加2000，单据的`billBalance`减少2000，结果如下：

        ![](images/test19.png)  
        ![](images/test20.png)  
        ![](images/test21.png)  

- `Bill`的`ability`为`false`时不能向`Bank`申请融资  

    ![](images/test23.png)  

- `financier`不为`Bank`时，不能申请融资  

    ![](images/test22.png)  

### 4、单据支付结算  
- 先调用`billMap`，查看`Tyre`的单据，如下：

    ![](images/test12.png)  

- `Car`调用`payBill`函数，`payee`为`Tyre`，`Car`的`property`减去`Tyre`的`bill`的`billBalance`（10000 - 5000 = 5000），`Tyre`增加相应金额(3000 + 5000 = 8000)，然后`Tyre`的`bill`被重新初始化
    
    ![](images/test25.png)  
    ![](images/test252.png)  
    ![](images/test253.png)  

- 非`Car`公司不能调用此函数  

    ![](images/test24.png)  

- `bill`的`ability`为`false`时不用还款  

    ![](images/test26.png)  
    ![](images/test27.png)  

- 公司`property`不足时不能结算  

    ![](images/test28.png)  
    ![](images/test29.png)  

## 四、后端（Java）
通过`FISCO BCOS`控制台提供的方法先将合约转换为`Java`类([源码地址](https://github.com/luji17343080/Lucky-SupplyChain/blob/master/src/SupplyChain.java))：  
- 将合约文件放入`fisco-bcos/console/contracts/solidity`下  
    
    ![](images/h0.png)  

- 在`console`目录下指定包名运行`sol2java.sh`脚本
```
    $ cd ~/fisco/console
    $ ./sol2java.sh org.com.fisco
```  

![](images/h1.png)  

- 运行成功之后，将会在console/contracts/sdk目录生成java、abi和bin目录，如下所示：
    
    ![](images/h2.png)  

- 在`fisco-bcos`上部署`SupplyChain.sol`

    ![](images/h4.png)  


## 五、前端界面展示（VUE.js）
前端使用vue框架搭建，npm安装框架

- 首先安装npm：sudo apt install npm(依赖nodejs，所以需要先安装node)
- 然后安装vue-cli脚手架：sudo npm install -g vue-cli（-g为安装到全局）
   - node和npm版本要在4以上（node -v, npm -v）
   - 由于最初的源使用npm install命令时非常的慢，所以可以通过命令npm config set registry https://registry.npm.taobao.org切换阿里的源
   - 通过npm config get registry查看npm的源
- 然后进入vue项目中（含vue的目录结构），通过命令npm install安装依赖
- 通过命令npm run dev在本地端口8080启动项目  
**……**
## 六、心得体会
这次作业虽然真正只实现了`链端`，但收获还是蛮多的，学习了一门新的语言`solidity`，在有其他语言的基础下，`solidity`的学习还是比较容易的，只是要注意一些特别的变量和函数而已，像`address`、`bytes`、`event`、`emit`等。除此之外，还通过使用`FISCO BCOS`和`WEBASE`了解了区块链`链端`的知识，包括`链的搭建`，`合约的部署`以及`合约的调用`等等。
