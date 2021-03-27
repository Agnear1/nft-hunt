# nft-hunt

Our idea is to create a real world NFT hunt. 
Game Perspective
NFT Creator
Place an NFT somewhere on the map (testnet).
NFT Treasure Hunter
Find an NFT somewhere on the map (testnet).
Rules
→ Users will travel to predetermined locations to collect NFT’s representing those areas. 
→ The corresponding NFT will cost some token in order to acquire, and the only way to access the possibility of buying these tokens is by being in a very specific location. 
→ If the user would like to purchase the NFT, they can initiate the smart contract and exchange some coins in their wallet for the corresponding NFT at that location.
Initial Steps
After looking into the two leading geolocation projects, XYO and FOAM, it is obvious that the space has some serious development issues. Both of these projects are plagued by their inability to produce a dynamic location. This severely limits the usability of the network and renders it almost useless for our purposes. 

Use External adapter to Google maps API for Geolocation data

As a temporary solution it is going to be best to use GPS data from a user’s phone. This would look like the following:
A user walks up to a predetermined location 
The user sends its location data to an oracle
The oracle sends the GPS data to the smart contract with the corresponding location specifications. 
Use VRF to generate NFT’s on different parts on the planet. One could be generated for every square mile. Can increase NFT generation based on density of population in those areas.(Backend)
If the parameters are met in the contract, the user will be given the ability to purchase the smart contracts bounty NFT with ether
The users wallet is then credited with the NFT
That contract expires
Takes a certain amount of time for that NFT to become purchasable in that location again.
Include a library (leaflet) to map, where the NFT’s will be located, show distance from current user location. (Front end)

