const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("DAppStore", function () {
  it("Should return the new greeting once it's changed", async function () {
    const DAppStore = await ethers.getContractFactory("DAppStore");
    const dAppStore = await DAppStore.deploy();
    await dAppStore.deployed();

    // expect(await greeter.greet()).to.equal("Hello, world!");

    // const setGreetingTx = await greeter.setGreeting("Hola, mundo!");

    // wait until the transaction is mined
    // await setGreetingTx.wait();

    // expect(await greeter.greet()).to.equal("Hola, mundo!");

    const submitAppTx = await dAppStore.submitApp("Uniswap", "IPFSHASH", "ADDDATA")
    await submitAppTx.wait();

    const appOutput = await dAppStore.getAppData(0);
    console.log(appOutput)
  });
});
