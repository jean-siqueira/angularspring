import { Person } from './../lesson01-classes/person';
import { DaoInterface } from './dao.interface';

export class Dao<T> implements DaoInterface<T>{

    tableName:string;

    insert(object: T):boolean{
        console.log('inserting...');
        return true;
    }

    update(object: T){
        return true;
    }

    delete(id: number):boolean{
        return true;
    }

    find(id: number): T{
        return null;
    }

    findAll():[T]{
        return [null];
    }

}