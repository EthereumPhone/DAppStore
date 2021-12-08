import { BigInt, JSONValue } from "@graphprotocol/graph-ts"
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
  entity.appAddData = event.params.appAddData
  entity.save()
  /** 
  const headers = new Map<string, string>()

  http.get(event.params.appAddData.toString(), headers, (error, body) => {
    let jsonObj: JSON.Obj = <JSON.Obj>(JSON.parse(body));
    entity!.description = jsonObj.getString("description")!.valueOf()
    entity!.developer = jsonObj.getString("description")!.valueOf()
    entity!.type = jsonObj.getString("description")!.valueOf()
    entity!.category = jsonObj.getString("description")!.valueOf()
    entity!.images = jsonObj.getString("images")!.valueOf()

    // Entities can be written to the store with `.save()`
    entity!.save()
  });
  */
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
  entity.appAddData = event.params.appAddData
  entity.save()
  /** 
  const headers = new Map<string, string>()

  http.get(event.params.appAddData.toString(), headers, (error, body) => {
    let jsonObj: JSON.Obj = <JSON.Obj>(JSON.parse(body));
    entity!.description = jsonObj.getString("description")!.valueOf()
    entity!.developer = jsonObj.getString("description")!.valueOf()
    entity!.type = jsonObj.getString("description")!.valueOf()
    entity!.category = jsonObj.getString("description")!.valueOf()
    entity!.images = jsonObj.getString("images")!.valueOf()

    // Entities can be written to the store with `.save()`
    entity!.save()
  });
  */
}
