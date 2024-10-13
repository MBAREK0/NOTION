<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Page Title</title>
    <!-- Flowbite CSS -->
    <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.3/dist/tailwind.min.css" rel="stylesheet">
    <%--    <link rel="stylesheet" href="<%= request.getContextPath() %>/path/to/your/custom/styles.css" />--%>
</head>
<body>

<jsp:include page="../layout/aside.jsp"    />
<div class="p-4 sm:ml-64">
    <div class="p-4 rounded-lg dark:border-gray-700">
        <div class=" mb-4 mt-10">
            <%--content--%>
            <form class="max-w-2xl mx-auto h-screen " action="<%= request.getContextPath() %>/inbox?action=accept_request" method="post">
                <input type="hidden" name="task_id" value="${task.id}">
                <input type="hidden" name="request_id" value="${taskModificationRequest.id}">
                <div class="relative z-0 w-full mb-5 group">
                    <label for="states-single" class="block mb-2 text-sm font-medium text-gray-900">Select New user to assign the task</label>
                    <select class=" selectOne w-full" name="user_id" id="states-single">
                        <option value="">Select user</option>
                        <c:forEach var="user" items="${users}">
                            <option value="${user.id}">${user.username}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
            </form>
        </div>

    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
