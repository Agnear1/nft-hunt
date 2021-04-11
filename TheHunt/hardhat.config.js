require("@nomiclabs/hardhat-waffle")
require("@nomiclabs/hardhat-web3")



// This is a sample Hardhat task. To learn how to create your own go to
// https://hardhat.org/guides/create-task.html
task("accounts", "Prints the list of accounts", async () => {
  const accounts = await ethers.getSigners();

  for (const account of accounts) {
    console.log(account.address);
  }
});

// task("addLong", "adds a longitude")
//   .addParam("contract", "The address of the contract")
//   .addParam("longitude", "The longitude")
//   .setAction(async taskArgs => {
//
//     contract.setLong(taskArgs.longitude);
//
//   });
//
// // This task enables the user to change address in the specified smart contract
// task("addLat","Adds a latitude")
//
//   .addParam("contract", "The address of the contract")
//   .addParam("latitude", "The latitide")
//   .setAction(async taskArgs => {
//
//     contract.setLat(taskArgs.latitude);
//
//   });
//
// task("checkCoords", "Checks the given coordinates against the given coordinates")
//   .addParam("contract", "The address of the contract")
//   .setAction(async taskArgs => {
//
//     const contractAddr = taskArgs.contract;
//     console.log("The coordinates:", taskArgs.contract.checkCoords(taskArgs.contract.latitude,taskArgs.contract.longitude))
//
//   });
// You need to export an object to set up your config
// Go to https://hardhat.org/config/ to learn more

/**
 * @type import('hardhat/config').HardhatUserConfig
 */
 module.exports = {
  solidity: {
    compilers: [
      {
        version: "0.8.0"
      },
      {
        version: "0.6.0",
        settings: { } 
      },
      {
        version: "0.7.0"
      },
    ]
  }
};
