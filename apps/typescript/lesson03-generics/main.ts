import { DaoInterface } from "./dao.interface";
import { Person } from "../lesson01-classes/person";
import { Dao } from "./dao";

let dao: DaoInterface<Person> = new Dao<Person>();
let person: Person = new Person('Débora SIlvana');

dao.insert(person);