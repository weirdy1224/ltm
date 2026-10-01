import { User } from './user.model';

export interface Customer {
  customerId?: number;
  customerName?: string;
  information?: string;
  user?: User;
}
