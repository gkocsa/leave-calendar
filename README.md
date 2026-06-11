# Team Leave Calendar

	A simple full-stack web application for managing team leave requests and viewing a weekly on-call rotation.

	## Features

	* View team members
	* Create leave requests
	* List leave requests
	* Filter leave requests by team member or status
	* Approve or reject leave requests
	* Add and edit one comment per leave request
	* Delete leave requests
	* Prevent overlapping leave requests for the same team member
	* View weekly on-call schedule
	* Highlight conflicts when the on-call person has approved leave

	## Tech Stack

	* Java 21
	* Spring Boot
	* Spring Data JPA / Hibernate
	* PostgreSQL
	* React + Vite
	* Docker Compose

	## Running the Application

	You can run the application using Docker.

	Make sure the following ports are free:

	* 8080
	* 5173
	* 5434

	From the project root:

	```bash
	docker compose up --build
	```

	Then open:

	```text
	Frontend: http://localhost:5173
	Backend:  http://localhost:8080
	```

	Example backend endpoint:

	```text
	http://localhost:8080/api/team-members
	```

	## Assumptions

	* No authentication is required.
	* Anyone using the app can approve or reject leave requests.
	* Leave dates are inclusive, so one-day leaves are possible.
	* On-call weeks run Monday to Sunday.
	* Only approved leave creates on-call conflicts.
	* Each leave request can have one editable comment.

	## Optional Improvements Completed

	* Endpoints for deleting and updating leave requests
	* Dockerization
	* Filtering by team member or status
	* Editable leave request comments
	* Basic automated backend tests
	* REST API documentation

	## (Optional) Features Not Completed

	- (Optional) Calendar month view 
	- (Optional) Automatic on-call replacement suggestion 
	- (Optional) Leave approval workflow / Better than basic requirments but could be improved
	- (Optional) Better visual conflict highlighting / Better than basic requirments but could be improved


	## REST API Documentation

	Base URL:

	```text
	http://localhost:8080
	```

	### Team Members

	#### Get all active team members

	```http
	GET /api/team-members
	```

	Example response:

	```json
	[
	  {
	    "id": 1,
	    "name": "Alice",
	    "rotationPosition": 1,
	    "active": true
	  }
	]
	```

	### Leave Requests

	#### Get all leave requests

	```http
	GET /api/leave-requests
	```

	#### Filter leave requests by team member

	```http
	GET /api/leave-requests?teamMemberId=1
	```

	#### Filter leave requests by status

	```http
	GET /api/leave-requests?status=APPROVED
	```

	Allowed status values:

	```text
	PENDING
	APPROVED
	REJECTED
	```

	#### Filter leave requests by team member and status

	```http
	GET /api/leave-requests?teamMemberId=1&status=APPROVED
	```

	#### Create a leave request

	```http
	POST /api/leave-requests
	```

	Example request body:

	```json
	{
	  "teamMemberId": 1,
	  "startDate": "2026-07-01",
	  "endDate": "2026-07-05",
	  "reason": "Vacation"
	}
	```

	Example response:

	```json
	{
	  "id": 1,
	  "teamMemberId": 1,
	  "teamMemberName": "Alice",
	  "startDate": "2026-07-01",
	  "endDate": "2026-07-05",
	  "reason": "Vacation",
	  "status": "PENDING",
	  "comment": null
	}
	```

	#### Update a leave request

	```http
	PUT /api/leave-requests/{id}
	```

	Example request body:

	```json
	{
	  "teamMemberId": 1,
	  "startDate": "2026-07-02",
	  "endDate": "2026-07-06",
	  "reason": "Updated vacation"
	}
	```

	#### Update leave request status

	```http
	PATCH /api/leave-requests/{id}/status
	```

	Example request body:

	```json
	{
	  "status": "APPROVED"
	}
	```

	#### Update leave request comment

	```http
	PATCH /api/leave-requests/{id}/comment
	```

	Example request body:

	```json
	{
	  "comment": "Approved after discussion with the team."
	}
	```

	#### Delete a leave request

	```http
	DELETE /api/leave-requests/{id}
	```

	### On-Call Schedule

	#### Get on-call schedule

	```http
	GET /api/on-call?from=2026-07-01&weeks=5
	```

	Example response:

	```json
	[
	  {
	    "weekStart": "2026-07-06",
	    "weekEnd": "2026-07-12",
	    "teamMemberId": 3,
	    "teamMemberName": "Charlie",
	    "hasConflict": false,
	    "conflicts": []
	  }
	]
	```
