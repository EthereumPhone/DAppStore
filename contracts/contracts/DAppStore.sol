//SPDX-License-Identifier: Unlicense
pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "@openzeppelin/contracts/access/AccessControl.sol";

contract DAppStore is Initializable, AccessControl {
    
    bytes32 public constant ADMIN_ROLE = keccak256("ADMIN_ROLE");
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
    event DeleteApp(uint appID);

    function initialize(uint _amountToPay, address[] calldata admins) public initializer {
        amountToPay = _amountToPay;
        for(uint i = 0;i<admins.length;i++) {
            _setupRole(ADMIN_ROLE, admins[i]);
        }
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

    function verifyDApp(uint _appID) public onlyRole(ADMIN_ROLE) {
        require(appVerified[_appID] == false, "Already verified");
        appVerified[_appID] = true;
        emit VerifyApp(_appID);
    }

    function deleteApp(uint _appID) public {
        emit DeleteApp(_appID);
    }

    function transferAppOwner(address _newOwner, uint _appID) public {
        appOwners[_appID] = _newOwner;
    }
    
    function addAdmin(address _newAdmin) public onlyRole(ADMIN_ROLE) {
        grantRole(ADMIN_ROLE, _newAdmin);
    }

}
