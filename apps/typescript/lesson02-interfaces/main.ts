import { Person } from './../lesson01-classes/person';
import { DaoInterface } from './dao.interface';
import { PersonDao } from './person-dao';

let personDao: DaoInterface = new PersonDao();
let person: Person = new Person('Silvana');

personDao.insert(person);