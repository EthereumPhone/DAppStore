import { ipfs,json, JSONValue, Value } from "@graphprotocol/graph-ts"
import { DAppStore, NewApp, UpdateApp } from "../generated/DAppStore/DAppStore"
import { App } from "../generated/schema"
//import { http } from 'as-http'
//import { JSON } from "assemblyscript-json"; 

export function handleNewApp(event: NewApp): void { 
  // Entities can be loaded from the store using a string ID; this ID
  // needs to be unique across all entities of the same type
  let entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (!entity) {
    entity = new App(event.params.appID.toString())
  }
  // Entity fields can be set based on event parameters
  entity.appOwner = event.params.appOwner
  entity.appName = event.params.appName
  entity.appIPFSHash = event.params.appIPFSHash
  entity.save()
  ipfs.mapJSON(event.params.appAddData, "processItem", Value.fromString(event.params.appID.toString()))
  

}

export function handleUpdateApp(event: UpdateApp): void {
  // Entities can be loaded from the store using a string ID; this ID
  // needs to be unique across all entities of the same type
  let entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (!entity) {
    entity = new App(event.params.appID.toString())
  }

  // Entity fields can be set based on event parameters
  entity.appOwner = event.params.appOwner
  entity.appName = event.params.appName
  entity.appIPFSHash = event.params.appIPFSHash
  let obj = json.fromBytes(ipfs.cat(event.params.appAddData)!).toObject()
  entity.description = obj.get("description")!.toString()
  entity.developer = obj.get("developer")!.toString()
  entity.type = obj.get("type")!.toString()
  entity.category = obj.get("category")!.toString()
  entity.logo = obj.get("logo")!.toString();
  entity.version = obj.get("version")!.toString();
  var allImg : string[] = []
  let allValues : JSONValue[] = obj.get("images")!.toArray()
  for(let i = 0;i<allValues.length;i++){
    allImg.push(allValues[i].toString())
  }
  entity.images = allImg
  entity.save()
}

export function processItem(value: JSONValue, userData: Value): void {
  // See the JSONValue documentation for details on dealing
  // with JSON values
  let obj = value.toObject()
  let id = obj.get('id')
  let title = obj.get('title')

  let entity = App.load(userData.toString())

  if (!entity) {
    entity = new App(userData.toString())
  }

  entity.description = obj.get("description")!.toString()
  entity.developer = obj.get("developer")!.toString()
  entity.type = obj.get("type")!.toString()
  entity.category = obj.get("category")!.toString()
  entity.logo = obj.get("logo")!.toString();
  entity.version = obj.get("version")!.toString();
  var allImg : string[] = []
  let allValues : JSONValue[] = obj.get("images")!.toArray()
  for(let i = 0;i<allValues.length;i++){
    allImg.push(allValues[i].toString())
  }
  entity.images = allImg
  entity.save()
}