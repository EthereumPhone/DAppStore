// THIS IS AN AUTOGENERATED FILE. DO NOT EDIT THIS FILE DIRECTLY.

import {
  TypedMap,
  Entity,
  Value,
  ValueKind,
  store,
  Bytes,
  BigInt,
  BigDecimal
} from "@graphprotocol/graph-ts";

export class App extends Entity {
  constructor(id: string) {
    super();
    this.set("id", Value.fromString(id));

    this.set("appOwner", Value.fromBytes(Bytes.empty()));
    this.set("appName", Value.fromString(""));
    this.set("appIPFSHash", Value.fromString(""));
    this.set("appAddData", Value.fromString(""));
  }

  save(): void {
    let id = this.get("id");
    assert(id != null, "Cannot save App entity without an ID");
    if (id) {
      assert(
        id.kind == ValueKind.STRING,
        "Cannot save App entity with non-string ID. " +
          'Considering using .toHex() to convert the "id" to a string.'
      );
      store.set("App", id.toString(), this);
    }
  }

  static load(id: string): App | null {
    return changetype<App | null>(store.get("App", id));
  }

  get id(): string {
    let value = this.get("id");
    return value!.toString();
  }

  set id(value: string) {
    this.set("id", Value.fromString(value));
  }

  get appOwner(): Bytes {
    let value = this.get("appOwner");
    return value!.toBytes();
  }

  set appOwner(value: Bytes) {
    this.set("appOwner", Value.fromBytes(value));
  }

  get appName(): string {
    let value = this.get("appName");
    return value!.toString();
  }

  set appName(value: string) {
    this.set("appName", Value.fromString(value));
  }

  get appIPFSHash(): string {
    let value = this.get("appIPFSHash");
    return value!.toString();
  }

  set appIPFSHash(value: string) {
    this.set("appIPFSHash", Value.fromString(value));
  }

  get appAddData(): string {
    let value = this.get("appAddData");
    return value!.toString();
  }

  set appAddData(value: string) {
    this.set("appAddData", Value.fromString(value));
  }
}