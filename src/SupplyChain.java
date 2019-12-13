package org.com.fisco;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class SupplyChain extends Contract {
    public static String BINARY = "608060405273ca35b7d915458ef540ade6068dfe2f44e8fa733c6000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507314723a09acff6d2a60dcdf7aa4aff308fddc160c600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550734b0897b0513fdc7c541b6d9d7e929c4e5364d2db600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555073583031d1113ad414f02576bd6afabfb302140225600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555073dd870fa1b7c4700f2bd7f44238821c26f7392148600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507395f452956282a5bfc2c8522bf23cd5525fcbcb9b600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060e060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600081526020016301312d6581526020016301312d6581526020016000815260200160001515815250600860008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff0219169083151502179055505050604080519081016040528060008019168152602001610bb8815250600f600082015181600001906000191690556020820151816001015550503480156200039057600080fd5b506200039b62000c42565b620003a562000c42565b620003af62000c42565b620003b962000c42565b620003c362000c42565b620003cd62000c42565b600f60408051908101604052908160008201546000191660001916815260200160018201548152505095507f436172000000000000000000000000000000000000000000000000000000000086600001906000191690816000191681525050612710866020018181525050600f60408051908101604052908160008201546000191660001916815260200160018201548152505094507f547972650000000000000000000000000000000000000000000000000000000085600001906000191690816000191681525050600f60408051908101604052908160008201546000191660001916815260200160018201548152505093507f487562000000000000000000000000000000000000000000000000000000000084600001906000191690816000191681525050600f60408051908101604052908160008201546000191660001916815260200160018201548152505092507f416c75690000000000000000000000000000000000000000000000000000000083600001906000191690816000191681525050600f60408051908101604052908160008201546000191660001916815260200160018201548152505091507f416c756d0000000000000000000000000000000000000000000000000000000082600001906000191690816000191681525050600f60408051908101604052908160008201546000191660001916815260200160018201548152505090507f42616e6b0000000000000000000000000000000000000000000000000000000081600001906000191690816000191681525050620186a081602001818152505085600660008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190600019169055602082015181600101559050508460066000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190600019169055602082015181600101559050508360066000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000190600019169055602082015181600101559050506008600760008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600282015481600201556003820154816003015560048201548160040155600582015481600501556006820160009054906101000a900460ff168160060160006101000a81548160ff021916908315150217905550905050600860076000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600282015481600201556003820154816003015560048201548160040155600582015481600501556006820160009054906101000a900460ff168160060160006101000a81548160ff021916908315150217905550905050600860076000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600282015481600201556003820154816003015560048201548160040155600582015481600501556006820160009054906101000a900460ff168160060160006101000a81548160ff02191690831515021790555090505050505050505062000c5f565b604080519081016040528060008019168152602001600081525090565b611ee38062000c6f6000396000f30060806040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b125828146100935780636f01c95e146100cd57806391b2208f1461011a5780639d859ec1146101f7578063a726a2e614610244578063b166fe9514610291578063d3b7b4c7146102f7578063ff4472c6146103a8575b600080fd5b34801561009f57600080fd5b506100a86103eb565b6040518083600019166000191681526020018281526020019250505060405180910390f35b3480156100d957600080fd5b50610118600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506103fd565b005b34801561012657600080fd5b5061015b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610acc565b604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018581526020018481526020018381526020018215151515815260200197505050505050505060405180910390f35b34801561020357600080fd5b50610242600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610b5b565b005b34801561025057600080fd5b5061028f600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050611152565b005b34801561029d57600080fd5b506102d2600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506116ee565b6040518083600019166000191681526020018281526020019250505060405180910390f35b34801561030357600080fd5b5061030c611712565b604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018681526020018581526020018481526020018381526020018215151515815260200197505050505050505060405180910390f35b3480156103b457600080fd5b506103e9600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061178f565b005b600f8060000154908060010154905082565b80600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154101515156104b7576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e742062696c6c2062616c616e63652100000000000081525060200191505060405180910390fd5b33600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600501600082825403925050819055506001600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201600082825401925050819055506001600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002016000828254019250508190555033600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050160008282540192505081905550600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160009054906101000a900460ff16600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160006101000a81548160ff0219169083151502179055507fc37c8ac0aee4f2b83144ed0c6bb3fe0f2b35eba04603af0258985c7abb2a6345338383600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154600760008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160009054906101000a900460ff16600760008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160009054906101000a900460ff1660405180806020018973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018781526020018681526020018581526020018415151515815260200183151515158152602001828103825260108152602001807f5472616e7366657220612042696c6c21000000000000000000000000000000008152506020019850505050505050505060405180910390a15050565b60076020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030154908060040154908060050154908060060160009054906101000a900460ff16905087565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141515610c46576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f506c65617365206170706c7920746f207468652062616e6b20666f722066696e81526020017f616e63696e67000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b60011515600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160009054906101000a900460ff161515141515610d37576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001807f42696c6c7320617265206e6f7420656c696769626c6520666f722066696e616e81526020017f63696e672100000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b80600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206005015410151515610e17576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260388152602001807f54686520616d6f756e74206f662066696e616e63696e6720657863656564732081526020017f7468652062616c616e6365206f66207468652062696c6c21000000000000000081525060400191505060405180910390fd5b80600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001016000828254019250508190555033600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002016000828254019250508190555080600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600501600082825403925050819055507f9a85162d1c51999b9ed76f39ae05cc8eaacb1c1458e853fa62256e933ed08600338383600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001015460405180806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001838152602001828103825260208152602001807f4170706c7920746f207468652062616e6b20666f722066696e616e63696e67218152506020019550505050505060405180910390a15050565b61115a611e4b565b8273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515156111fe576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f42696c6c2070617965652063616e6e6f7420626520796f757273656c6621000081525060200191505060405180910390fd5b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141515156112c4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f42696c6c2070617965652063616e6e6f742062652062616e6b2100000000000081525060200191505060405180910390fd5b600181604001818152505033816000019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff161415156113775760008160c0019015159081151581525050611389565b60018160c00190151590811515815250505b82816020019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff1681525050818160a001818152505080600760003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff02191690831515021790555090505080600760008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff0219169083151502179055509050507ff78b7abe1ac38ace67b80c2b38cf419976c06b0e63077f33290e2f7cb696fa5a816000015184848460c0015160405180806020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001831515151581526020018281038252600e8152602001807f43726561746520612062696c6c210000000000000000000000000000000000008152506020019550505050505060405180910390a1505050565b60066020528060005260406000206000915090508060000154908060010154905082565b60088060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030154908060040154908060050154908060060160009054906101000a900460ff16905087565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515611855576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f596f7520646f6e2774206861766520746f207061792062696c6c21000000000081525060200191505060405180910390fd5b60011515600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060060160009054906101000a900460ff1615151480156118fa57506000600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154115b1515611994576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260268152602001807f596f7520646f6e2774206861766520746f20706179206e6f206162696c69747981526020017f2062696c6c21000000000000000000000000000000000000000000000000000081525060400191505060405180910390fd5b600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060050154600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010154111515611a8f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f496e73756666696369656e7420636f6d70616e7920617373657473210000000081525060200191505060405180910390fd5b600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206005015490506008600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600282015481600201556003820154816003015560048201548160040155600582015481600501556006820160009054906101000a900460ff168160060160006101000a81548160ff02191690831515021790555090505080600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001016000828254039250508190555080600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101600082825401925050819055507f4e91bfc266d06b2bcd93d298c500ed8ba0c7e7e05d037cc624ff933dd7624de4338383600660003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010154600660008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001015460405180806020018773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018581526020018481526020018381526020018281038252600b8152602001807f50617920612062696c6c21000000000000000000000000000000000000000000815250602001965050505050505060405180910390a15050565b60e060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff1681526020016000815260200160008152602001600081526020016000815260200160001515815250905600a165627a7a72305820589153734373a4b8f00df004bbb13ebf227fd8a67dffdfd2959833e5da62895c0029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"com__\",\"outputs\":[{\"name\":\"companyName\",\"type\":\"bytes32\"},{\"name\":\"property\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"receiver\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"}],\"name\":\"billMap\",\"outputs\":[{\"name\":\"payer\",\"type\":\"address\"},{\"name\":\"payee\",\"type\":\"address\"},{\"name\":\"transactionCounts\",\"type\":\"uint256\"},{\"name\":\"paymentTime\",\"type\":\"uint256\"},{\"name\":\"repaymentTime\",\"type\":\"uint256\"},{\"name\":\"billBalance\",\"type\":\"uint256\"},{\"name\":\"ability\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"financier\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"applyFinancing\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"payee\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"createBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"}],\"name\":\"companyMap\",\"outputs\":[{\"name\":\"companyName\",\"type\":\"bytes32\"},{\"name\":\"property\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"bill__\",\"outputs\":[{\"name\":\"payer\",\"type\":\"address\"},{\"name\":\"payee\",\"type\":\"address\"},{\"name\":\"transactionCounts\",\"type\":\"uint256\"},{\"name\":\"paymentTime\",\"type\":\"uint256\"},{\"name\":\"repaymentTime\",\"type\":\"uint256\"},{\"name\":\"billBalance\",\"type\":\"uint256\"},{\"name\":\"ability\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"payee\",\"type\":\"address\"}],\"name\":\"payBill\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"payer\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"payee\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"ability\",\"type\":\"bool\"}],\"name\":\"Bill_\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"transferer\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"receiver\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"payerBillBalance\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"payeeBillBalance\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"payerBillAbility\",\"type\":\"bool\"},{\"indexed\":false,\"name\":\"payeeBillAbility\",\"type\":\"bool\"}],\"name\":\"Tran_\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"company\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"financier\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"companyPro\",\"type\":\"uint256\"}],\"name\":\"Fina_\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"msg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"payer\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"payee\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"payerPro\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"payeePro\",\"type\":\"uint256\"}],\"name\":\"Pay_\",\"type\":\"event\"}]";

    public static final String FUNC_COM__ = "com__";

    public static final String FUNC_TRANSFERBILL = "transferBill";

    public static final String FUNC_BILLMAP = "billMap";

    public static final String FUNC_APPLYFINANCING = "applyFinancing";

    public static final String FUNC_CREATEBILL = "createBill";

    public static final String FUNC_COMPANYMAP = "companyMap";

    public static final String FUNC_BILL__ = "bill__";

    public static final String FUNC_PAYBILL = "payBill";

    public static final Event BILL__EVENT = new Event("Bill_", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event TRAN__EVENT = new Event("Tran_", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event FINA__EVENT = new Event("Fina_", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAY__EVENT = new Event("Pay_", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SupplyChain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SupplyChain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SupplyChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SupplyChain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple2<byte[], BigInteger>> com__() {
        final Function function = new Function(FUNC_COM__, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<byte[], BigInteger>>(
                new Callable<Tuple2<byte[], BigInteger>>() {
                    @Override
                    public Tuple2<byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transferBill(String receiver, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferBill(String receiver, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferBillSeq(String receiver, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFERBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(receiver), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> billMap(String param0) {
        final Function function = new Function(FUNC_BILLMAP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(
                new Callable<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> applyFinancing(String financier, BigInteger amount) {
        final Function function = new Function(
                FUNC_APPLYFINANCING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(financier), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void applyFinancing(String financier, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_APPLYFINANCING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(financier), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String applyFinancingSeq(String financier, BigInteger amount) {
        final Function function = new Function(
                FUNC_APPLYFINANCING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(financier), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> createBill(String payee, BigInteger amount) {
        final Function function = new Function(
                FUNC_CREATEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void createBill(String payee, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_CREATEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String createBillSeq(String payee, BigInteger amount) {
        final Function function = new Function(
                FUNC_CREATEBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple2<byte[], BigInteger>> companyMap(String param0) {
        final Function function = new Function(FUNC_COMPANYMAP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<byte[], BigInteger>>(
                new Callable<Tuple2<byte[], BigInteger>>() {
                    @Override
                    public Tuple2<byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> bill__() {
        final Function function = new Function(FUNC_BILL__, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(
                new Callable<Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> payBill(String payee) {
        final Function function = new Function(
                FUNC_PAYBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void payBill(String payee, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PAYBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String payBillSeq(String payee) {
        final Function function = new Function(
                FUNC_PAYBILL, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(payee)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<Bill_EventResponse> getBill_Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BILL__EVENT, transactionReceipt);
        ArrayList<Bill_EventResponse> responses = new ArrayList<Bill_EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Bill_EventResponse typedResponse = new Bill_EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.payer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.payee = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.ability = (Boolean) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerBill_EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(BILL__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerBill_EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(BILL__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<Tran_EventResponse> getTran_Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRAN__EVENT, transactionReceipt);
        ArrayList<Tran_EventResponse> responses = new ArrayList<Tran_EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Tran_EventResponse typedResponse = new Tran_EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.transferer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.payerBillBalance = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.payeeBillBalance = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.payerBillAbility = (Boolean) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.payeeBillAbility = (Boolean) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTran_EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRAN__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerTran_EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRAN__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<Fina_EventResponse> getFina_Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FINA__EVENT, transactionReceipt);
        ArrayList<Fina_EventResponse> responses = new ArrayList<Fina_EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Fina_EventResponse typedResponse = new Fina_EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.company = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.financier = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.companyPro = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerFina_EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FINA__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerFina_EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FINA__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<Pay_EventResponse> getPay_Events(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAY__EVENT, transactionReceipt);
        ArrayList<Pay_EventResponse> responses = new ArrayList<Pay_EventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Pay_EventResponse typedResponse = new Pay_EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.msg = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.payer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.payee = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.payerPro = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.payeePro = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerPay_EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAY__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerPay_EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAY__EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static SupplyChain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SupplyChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SupplyChain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SupplyChain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SupplyChain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SupplyChain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SupplyChain> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SupplyChain> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Bill_EventResponse {
        public Log log;

        public String msg;

        public String payer;

        public String payee;

        public BigInteger amount;

        public Boolean ability;
    }

    public static class Tran_EventResponse {
        public Log log;

        public String msg;

        public String transferer;

        public String receiver;

        public BigInteger amount;

        public BigInteger payerBillBalance;

        public BigInteger payeeBillBalance;

        public Boolean payerBillAbility;

        public Boolean payeeBillAbility;
    }

    public static class Fina_EventResponse {
        public Log log;

        public String msg;

        public String company;

        public String financier;

        public BigInteger amount;

        public BigInteger companyPro;
    }

    public static class Pay_EventResponse {
        public Log log;

        public String msg;

        public String payer;

        public String payee;

        public BigInteger amount;

        public BigInteger payerPro;

        public BigInteger payeePro;
    }
}