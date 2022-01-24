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
    this.set("description", Value.fromString(""));
    this.set("developer", Value.fromString(""));
    this.set("type", Value.fromString(""));
    this.set("category", Value.fromString(""));
    this.set("logo", Value.fromString(""));
    this.set("version", Value.fromString(""));
    this.set("images", Value.fromStringArray(new Array(0)));
    this.set("status", Value.fromString(""));
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

  get description(): string {
    let value = this.get("description");
    return value!.toString();
  }

  set description(value: string) {
    this.set("description", Value.fromString(value));
  }

  get developer(): string {
    let value = this.get("developer");
    return value!.toString();
  }

  set developer(value: string) {
    this.set("developer", Value.fromString(value));
  }

  get type(): string {
    let value = this.get("type");
    return value!.toString();
  }

  set type(value: string) {
    this.set("type", Value.fromString(value));
  }

  get category(): string {
    let value = this.get("category");
    return value!.toString();
  }

  set category(value: string) {
    this.set("category", Value.fromString(value));
  }

  get logo(): string {
    let value = this.get("logo");
    return value!.toString();
  }

  set logo(value: string) {
    this.set("logo", Value.fromString(value));
  }

  get version(): string {
    let value = this.get("version");
    return value!.toString();
  }

  set version(value: string) {
    this.set("version", Value.fromString(value));
  }

  get images(): Array<string> {
    let value = this.get("images");
    return value!.toStringArray();
  }

  set images(value: Array<string>) {
    this.set("images", Value.fromStringArray(value));
  }

  get verified(): boolean {
    let value = this.get("verified");
    return value!.toBoolean();
  }

  set verified(value: boolean) {
    this.set("verified", Value.fromBoolean(value));
  }

  get status(): string {
    let value = this.get("status");
    return value!.toString();
  }

  set status(value: string) {
    this.set("status", Value.fromString(value));
  }
}

export class DAppStore extends Entity {
  constructor(id: string) {
    super();
    this.set("id", Value.fromString(id));

    this.set("ipfsHash", Value.fromString(""));
  }

  save(): void {
    let id = this.get("id");
    assert(id != null, "Cannot save DAppStore entity without an ID");
    if (id) {
      assert(
        id.kind == ValueKind.STRING,
        "Cannot save DAppStore entity with non-string ID. " +
          'Considering using .toHex() to convert the "id" to a string.'
      );
      store.set("DAppStore", id.toString(), this);
    }
  }

  static load(id: string): DAppStore | null {
    return changetype<DAppStore | null>(store.get("DAppStore", id));
  }

  get id(): string {
    let value = this.get("id");
    return value!.toString();
  }

  set id(value: string) {
    this.set("id", Value.fromString(value));
  }

  get ipfsHash(): string {
    let value = this.get("ipfsHash");
    return value!.toString();
  }

  set ipfsHash(value: string) {
    this.set("ipfsHash", Value.fromString(value));
  }
}
