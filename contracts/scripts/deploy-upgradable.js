// We require the Hardhat Runtime Environment explicitly here. This is optional
// but useful for running the script in a standalone fashion through `node <script>`.
//
// When running the script with `npx hardhat run <script>` you'll find the Hardhat
// Runtime Environment's members available in the global scope.
const {ethers, upgrades} = require("hardhat"); 

async function main() {
  // Hardhat always runs the compile task when running scripts with its command
  // line interface.
  //
  // If this script is run directly using `node` you may want to call compile
  // manually to make sure everything is compiled
  // await hre.run('compile');

  // We get the contract to deploy
  const DAppStore = await ethers.getContractFactory("DAppStore");
  const dAppStore = await upgrades.deployProxy(DAppStore, [1000, ["0x2247d5d238d0f9d37184d8332aE0289d1aD9991b", "0x3a4e6eD8B0F02BFBfaA3C6506Af2DB939eA5798c"]])

  await dAppStore.deployed();

  console.log("DAppStore-Proxy deployed to:", dAppStore.address);
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
