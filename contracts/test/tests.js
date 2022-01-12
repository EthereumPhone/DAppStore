const { expect } = require("chai");
const { ethers, upgrades } = require("hardhat");

describe("DAppStore", function () {
  let DAppStore;
  let dAppStore;
  const amountToPay = "0.01"
  it("should create an dapp", async function () {
    const [owner] = await ethers.getSigners();
    DAppStore = await ethers.getContractFactory("DAppStore");
    dAppStore = await upgrades.deployProxy(DAppStore, [ethers.utils.parseEther(amountToPay), [owner.address]])
    const name = "Uniswap";
    await dAppStore.deployed();
    const submitAppTx = await dAppStore.submitDApp(name, "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", { value: ethers.utils.parseEther(amountToPay) })
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
    const updateAppTx = await dAppStore.updateDApp(0, name, "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy", "QmXnnyufdzAWL5CqZ2RnSNgPbvCc1ALT73s6epPrRnZ1Xy")
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

  it("should have added admin", async function () {
    await dAppStore.deployed();
    const [owner] = await ethers.getSigners();
    const getHasRole = await dAppStore.hasRole("0xa49807205ce4d355092ef5a8a18f56e8913cf4a201fbe287825b095693c21775", owner.address);
    expect(getHasRole).to.equal(true)
  });

  it("should delete app", async function () {
    await dAppStore.deployed();
    const deleteAppTx = await dAppStore.deleteApp(0);
    await deleteAppTx.wait()
  });
});
