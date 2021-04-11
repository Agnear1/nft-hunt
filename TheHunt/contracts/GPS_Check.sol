//SPDX-License-Identifier: Unlicense
pragma solidity ^0.6.0;

import "hardhat/console.sol";



// REMEMBER
// LATITUDE THEN LONGITUDE
contract GPS_Check {
  int256 public latitude;
  int256 public longitude;

  //LATITUDE
  int256 northLat = 44.487260*1000000000000;
  int256 southLat = 44.469010*1000000000000;

  //LONGITUDE
  int256 westLong = -73.223653*1000000000000;
  int256 eastLong = -73.192390*1000000000000;

  constructor (int256 _latitude,int256  _longitude) public {
      latitude = _latitude;
      longitude = _longitude;

  }

  
  // This function will check the inputted coordinates to see if they
  // fit inside the predetermined box
  function checkCoords(int256 _latitude, int256 _longitude) public view returns(bool){
    bool longBool = false;
    bool latBool = false;

    // Check longitude coordinates
    // add requires here instead of IF statements?
    if(_latitude <= northLat && _latitude >= southLat)
      latBool = true;

    // Check latitude coordinates
    if(_longitude >= westLong && _longitude <= eastLong)
      longBool = true;

    // If the coordinates fit in the box then return true
    require(longBool == true && latBool == true, "Sorry, your current location does not allow you to access this NFT.");
      console.log("Your GPS coordinates are withint the accpetable range. You may purchase this NFT");
      return true;
  }

  function getLat() public view returns(int256){
    return latitude;
    
  }

  function getLong() public view returns(int256){
    return longitude;
    
  }


  function setLong(int _longitude) public {
    //console.logInt("Changing greeting from '%s' to '%s'", longitude, _longitude);
    longitude = _longitude;
  }

  function setLat(int _latitude) public {
    //console.logInt("Changing greeting from '%s' to '%s'", latitude, _latitude);
    latitude = _latitude;
  }
}
