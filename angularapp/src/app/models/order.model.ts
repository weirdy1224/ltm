import { Customer } from './customer.model';
import { Course } from './course.model';

export interface Order {
  orderId?: number;
  orderPrice?: number;
  courses?: Course[];
  customer?: Customer;
}
