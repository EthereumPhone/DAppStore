import { ipfs, json, store, Value } from "@graphprotocol/graph-ts"
import { NewApp, UpdateApp, VerifyApp, ReleaseApp, DeleteApp, StoreUpdate } from "../generated/DAppStore/DAppStore"
import { App, DAppStore } from "../generated/schema"
//import { http } from 'as-http'
//import { JSON } from "assemblyscript-json"; 
var entity: App | null;
var dappstoreEntity: DAppStore | null;
let allArr: string[] = []
export function handleNewApp(event: NewApp): void {
  let allImg: Value[] = []
  // Entities can be loaded from the store using a string ID; this ID
  // needs to be unique across all entities of the same type
  entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (!entity) {
    entity = new App(event.params.appID.toString())
  }
  // Entity fields can be set based on event parameters
  entity!.appOwner = event.params.appOwner
  entity!.appName = event.params.appName
  entity!.appIPFSHash = event.params.appIPFSHash
  let data = ipfs.cat(event.params.appAddData)
  entity!.verified = false;
  if (data !== null && entity !== null) {
    var obj = json.try_fromString(data.toString().replace("\n", "")).value.toObject()
    obj.entries.forEach(entry => {
      /** 
      if (entry.key === "images") {
        let allValues = entry.value.toArray()
        for (let i = 0;i<allValues.length;i++) {
          allImg[i] = Value.fromString(allValues[i].toString())
        }
        if (entity !== null) {
          entity.set(entry.key, Value.fromArray(allImg))
        }
      } else {
        if (entity !== null) {
          entity.set(entry.key, Value.fromString(entry.value.toString()));
        }
      }
      */
      if (entity !== null && !entry.key.startsWith("images")) {
        entity!.set(entry.key, Value.fromString(entry.value.toString()));
      }
      if (entry.key.startsWith("images")) {
        let allImg = entry.value.toArray()
        for (let i = 0; i < allImg.length; i++) {
          allArr.push(allImg[i].toString())
        }
        entity!.set(entry.key, Value.fromStringArray(allArr))
        allArr = []
      }
    })
  }
  entity!.status = "submitted"

  entity!.save()


}

export function handleUpdateApp(event: UpdateApp): void {
  let allImg: Value[] = []
  // Entities can be loaded from the store using a string ID; this ID
  // needs to be unique across all entities of the same type
  entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (!entity) {
    entity = new App(event.params.appID.toString())
  }
  // Entity fields can be set based on event parameters
  entity!.appOwner = event.params.appOwner
  entity!.appName = event.params.appName
  entity!.appIPFSHash = event.params.appIPFSHash
  let data = ipfs.cat(event.params.appAddData)
  if (data !== null && entity !== null) {
    var obj = json.try_fromString(data.toString().replace("\n", "")).value.toObject()
    obj.entries.forEach(entry => {
      /** 
      if (entry.key === "images") {
        let allValues = entry.value.toArray()
        for (let i = 0;i<allValues.length;i++) {
          allImg[i] = Value.fromString(allValues[i].toString())
        }
        if (entity !== null) {
          entity.set(entry.key, Value.fromArray(allImg))
        }
      } else {
        if (entity !== null) {
          entity.set(entry.key, Value.fromString(entry.value.toString()));
        }
      }
      */
      if (entity !== null && !entry.key.startsWith("images")) {
        //console.log(entry.key + " : " + entry.value.toString())
        entity!.set(entry.key, Value.fromString(entry.value.toString()));
      }
      if (entry.key.startsWith("images")) {
        let allImg = entry.value.toArray()
        for (let i = 0; i < allImg.length; i++) {
          allArr.push(allImg[i].toString())
        }
        entity!.set(entry.key, Value.fromStringArray(allArr))
        allArr = []
      }
    })
  }

  entity!.save()

}
export function verifyApp(event: VerifyApp): void {
  entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (!entity) {
    entity = new App(event.params.appID.toString())
  }

  entity!.verified = true;
  entity!.save();
}

export function releaseApp(event: ReleaseApp): void {
  entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (entity) {
    entity!.status = "released";
    entity!.save();
  }
}

export function deleteApp(event: DeleteApp): void {
  entity = App.load(event.params.appID.toString())

  // Entities only exist after they have been saved to the store;
  // `null` checks allow to create entities on demand
  if (entity) {
    store.remove("App", event.params.appID.toString())
  }
}

export function newDAppStore(event: StoreUpdate): void {
  dappstoreEntity = new DAppStore(event.params.versionCode.toString());
  dappstoreEntity!.ipfsHash = event.params.ipfsHash;
  dappstoreEntity!.save();

  var oldID = event.params.versionCode.toI64()-1
  var oldEntity = DAppStore.load(oldID.toString())
  if (oldEntity) {
    store.remove("DAppStore", oldID.toString());
  }
}