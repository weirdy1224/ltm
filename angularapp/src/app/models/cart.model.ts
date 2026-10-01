import { Customer } from './customer.model';
import { Course } from './course.model';

export interface Cart {
  cartId?: number;
  customer?: Customer;
  courses?: Course[];
  totalAmount?: number;
}
