//SPDX-License-Identifier: Unlicense
pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAppStore is Initializable {
    address public owner;
    uint256 public appID;
    uint public amountToPay;
    mapping(uint => address) appOwners;
    mapping(uint => string) appName;
    mapping(uint => string) appIPFSHash;
    mapping(uint => string) appAddData;
    mapping(uint => uint) submitTime;
    mapping(uint => bool) appVerified;

    event NewApp(uint appID, address appOwner, string appName, string appIPFSHash, string appAddData);
    event UpdateApp(uint appID, address appOwner, string appName, string appIPFSHash, string appAddData);
    event ReleaseApp(uint appID);
    event VerifyApp(uint appID);

    modifier _onlyOwner {
        require (msg.sender == owner, "Only owner");
        _;
    }

    function initialize(uint _amountToPay) public initializer {
        amountToPay = _amountToPay;
        // TODO: Replace with real address
        owner = msg.sender;
        appID = 0;
    }

    struct App {
        uint appID;
        address appOwner;
        string appName;
        string appIPFSHash;
        string appAddData;
    }

    function submitDApp(string memory _name, string memory _ipfsHash, string memory _additionalData) public payable {
        //require(msg.value == amountToPay, "Ether for lockup required");
        appOwners[appID] = msg.sender;
        appName[appID] = _name;
        appIPFSHash[appID] = _ipfsHash;
        appAddData[appID] = _additionalData;
        submitTime[appID] = block.timestamp;
        emit NewApp(appID, msg.sender, appName[appID], appIPFSHash[appID], appAddData[appID]);
        appID = appID + 1;
    }

    function updateDApp(uint _appID, string memory _name, string memory _ipfsHash, string memory _additionalData) public {
        require(appOwners[_appID] == msg.sender, "Not owner of dapp");
        appName[_appID] = _name;
        appIPFSHash[_appID] = _ipfsHash;
        appAddData[_appID] = _additionalData;
        emit UpdateApp(_appID, msg.sender, _name, _ipfsHash, _additionalData);
    }

    function getDAppData(uint _appID) external view returns(App memory) {
        return App(_appID, appOwners[_appID], appName[_appID], appIPFSHash[_appID], appAddData[_appID]);
    }

    function releaseDApp(uint _appID) public {
        require(appOwners[_appID] == msg.sender, "Not owner of dapp");
        /**
            The test-version on rinkeby does check for 48h passed.
         */
        //require(submitTime[_appID]+172800<block.timestamp, "DApp not passed 48h yet");
        emit ReleaseApp(_appID);
        payable(msg.sender).transfer(amountToPay);
    }

    function verifyDApp(uint _appID) public _onlyOwner {
        require(appVerified[_appID] == false, "Already verified");
        appVerified[_appID] = true;
        emit VerifyApp(_appID);
    }

}
