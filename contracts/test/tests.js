const { expect } = require("chai");
const { ethers, upgrades } = require("hardhat");

describe("DAppStore", function () {
  let DAppStore;
  let dAppStore;
  const amountToPay = "0.01"
  it("should create an dapp", async function () {
    DAppStore = await ethers.getContractFactory("DAppStore");
    dAppStore = await upgrades.deployProxy(DAppStore, [ethers.utils.parseEther(amountToPay)])
    const name = "Uniswap";
    await dAppStore.deployed();
    const submitAppTx = await dAppStore.submitDApp(name, "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "Exchange", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", { value: ethers.utils.parseEther(amountToPay) })
    await submitAppTx.wait();
    let balance = await ethers.provider.getBalance(dAppStore.address);
    console.log("Balance: ", balance)
    const appOutput = await dAppStore.getDAppData(0);
    console.log("First name: "+appOutput.appName)
    expect(appOutput.appName).to.equal(name);
  });
  
  
  it("should update an dapp", async function () {
    await dAppStore.deployed();
    const name = "Metaswap";
    const updateAppTx = await dAppStore.updateDApp(0, name, "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "Exchange", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy")
    await updateAppTx.wait();
    const appOutput = await dAppStore.getDAppData(0);
    console.log("Updated name:",appOutput.appName)
    expect(appOutput.appName).to.equal(name);
  });

  it("should release dapp" , async function () {
    await dAppStore.deployed();
    await ethers.provider.send("evm_increaseTime", [172800]);
    const releaseDappTx = await dAppStore.releaseDApp(0);
    await releaseDappTx.wait();
  });
});
