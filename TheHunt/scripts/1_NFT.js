const hre = require("hardhat");

async function main() {

  const BurlingtonNFT = await hre.ethers.getContractFactory("burlingtonNFT");
  const burlingtonNFT = await BurlingtonNFT.deploy();


}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main()
  .then(() => process.exit(0))
  .catch(error => {
    console.error(error);
    process.exit(1);
  });
