# springboot-ordermanagement
## Implements water ordering REST APIs so that farmers can request water to irrigate their farms.**

## To run the application execute command mvn springboot:run on the commandline

The aim of this project is to implement a water ordering API so farmers can request water to irrigate their farms. 
Farmers can use this API to place water orders, view existing water orders and cancel water orders before they are delivered.

A basic water order has the following attributes:
1. **farmId** – A unique ID for identifying a farm.
2. **Start date time** – The date and time when water should be delivered. Ex: Sunday, Dec 15, 2019 01:50:16 PM
3. **Duration** – The duration of the order (e.g. Duration of 3 hours means water will flow into the farm for 3 hours from the start date time). Ex: 3
**Sample farmer request json -**
{"farmName":"My own farm"}

**Sample order request json -**
{
   "farmerid":"1",
   "start":"Sunday, Dec 15, 2019 01:50:16 PM",
   "hours":"1"
}

## The project has the following features and functionality:
1. Implements a REST API to accept new orders from a farmer.
2. Implements an API for cancelling an existing order if it hasn’t been delivered.
3. Implements an API so farmers can query existing orders. When querying orders, the farmer should be able to see the status of each order.
    Possible status of a water order:
    1. **Requested** – “Order has been placed but not yet delivered.”
    2. **InProgress** – “Order is being delivered right now.
    3. **Delivered** – “Order has been delivered.”
    4. **Cancelled** – “Order was cancelled before delivery.”
4. The API ensures the water orders for a farm do not overlap.  For example, if Farm X already has an order for 30 Jan 2019 starting at 6am with a 3 hours duration, it should not allow Farm X to place an order starting at 8am on the same day.
5. To simulate water delivery, the application outputs a line each time the status of a water order changes.
    This include –
    1. When a new water order is placed;
    2. When a water order starts (Start date time of the order);
    3. When a water order is delivered (i.e. start date time + duration);
    4. When a water order is cancelled;
    
    As an example:
    1. Assume it is currently 10am, and a water order is created with a start time of 12pm and finishing at 2pm.
    2. At 10am, when the order is placed, your application’s log should print “New water order for farm xyz created”
    3. At 12pm, log should print “Water delivery to farm xyz started.”
    4. At 2pm, log should print “Water delivery to farm xyz stopped.”
6. In this project, all DAO objects like orders, etc are stored in memory. Orders/farmers or any other DAO data are not persisted between application restarts.
