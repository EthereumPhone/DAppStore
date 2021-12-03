//SPDX-License-Identifier: Unlicense
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAppStore is Initializable {
    // TODO: Replace with real address
    address public owner = address(0);
    uint256 public appID = 0;
    mapping(uint => address) appOwners;
    mapping(uint => string) appName;
    mapping(uint => string) appIPFSHash;
    mapping(uint => string) appAddData;

    event NewApp(uint appID, address appOwner, string appName, string appIPFSHash, string appAddData);
    event UpdateApp(uint appID, address appOwner, string appName, string appIPFSHash, string appAddData);

    modifier _onlyOwner {
        require (msg.sender == owner, "Only owner");
        _;
    }

    constructor() initializer {}

    struct App {
        uint appID;
        address appOwner;
        string appName;
        string appIPFSHash;
        string appAddData;
    }

    function submitApp(string memory _name, string memory _ipfsHash, string memory _additionalData) public {
        appOwners[appID] = msg.sender;
        appName[appID] = _name;
        appIPFSHash[appID] = _ipfsHash;
        appAddData[appID] = _additionalData;
        emit NewApp(appID, msg.sender, _name, _ipfsHash, _additionalData);
        appID = appID + 1;
    }

    function updateApp(uint _appID, string memory _name, string memory _ipfsHash, string memory _additionalData) public {
        require(appOwners[_appID] == msg.sender, "Not owner of app");
        appName[appID] = _name;
        appIPFSHash[appID] = _ipfsHash;
        appAddData[appID] = _additionalData;
        emit UpdateApp(appID, msg.sender, _name, _ipfsHash, _additionalData);
    }

    function getAppData(uint _appID) external view returns(App memory) {
        return App(_appID, appOwners[_appID], appName[_appID], appIPFSHash[_appID], appAddData[_appID]);
    }

}
