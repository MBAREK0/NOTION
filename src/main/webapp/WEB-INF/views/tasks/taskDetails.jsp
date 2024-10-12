<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

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
        <div class=" mb-4">
            <%--content--%>


                <ol class="relative text-gray-500 border-s border-gray-200 dark:border-gray-700 dark:text-gray-400">
                    <li class="mb-10 ms-6 flex items-center">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-green-200 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-green-900">
                            <svg class="w-3.5 h-3.5 text-green-500 dark:text-green-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 16 12">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5.917 5.724 10.5 15 1.5"/>
                            </svg>
                        </span>
                        <h3 class="font-medium leading-tight">${task.title}</h3>
                    </li>

                    <li class="mb-10 ms-6">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                           <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 20">
                                <path d="M16 1h-3.278A1.992 1.992 0 0 0 11 0H7a1.993 1.993 0 0 0-1.722 1H2a2 2 0 0 0-2 2v15a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2Zm-3 14H5a1 1 0 0 1 0-2h8a1 1 0 0 1 0 2Zm0-4H5a1 1 0 0 1 0-2h8a1 1 0 1 1 0 2Zm0-5H5a1 1 0 0 1 0-2h2V2h4v2h2a1 1 0 1 1 0 2Z"/>
                            </svg>
                        </span>
                        <h3 class="font-medium leading-tight">Description</h3>
                        <p class="text-sm">${task.description}</p>
                    </li>
                    <li class="mb-10 ms-6 flex items-center" >
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                          <svg class="flex-shrink-0 w-3.5 h-3.5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                              <path fill-rule="evenodd" clip-rule="evenodd" d="M16 8L8 0H0V8L8 16L16 8ZM4.5 6C5.32843 6 6 5.32843 6 4.5C6 3.67157 5.32843 3 4.5 3C3.67157 3 3 3.67157 3 4.5C3 5.32843 3.67157 6 4.5 6Z" fill="currentColor"/>
                          </svg>
                        </span>
                        <c:forEach var="tag" items="${task.tags}">
                            <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">${tag.name}</span>
                        </c:forEach>
                    </li>

                    <li class="mb-10 ms-6 flex  items-center">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
                                <path d="M12 0C5.37 0 0 5.37 0 12s5.37 12 12 12 12-5.37 12-12S18.63 0 12 0zm1.5 18h-3v-8h3v8zm0-10h-3V5h3v3z"/>
                            </svg>
                        </span>
                        <c:choose>
                            <c:when test="${task.status == 'pending'}">
                                <span class="bg-blue-100 text-blue-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300">Pending</span>
                            </c:when>
                            <c:when test="${task.status == 'in_progress'}">
                                <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">In Progress</span>
                            </c:when>
                            <c:when test="${task.status == 'completed'}">
                                <span class="bg-green-100 text-green-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-green-900 dark:text-green-300">Completed</span>
                            </c:when>
                            <c:when test="${task.status == 'overdue'}">
                                <span class="bg-red-100 text-red-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-red-900 dark:text-red-300">Overdue</span>
                            </c:when>
                            <c:otherwise>
                                <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">Unknown</span>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <li class="mb-10 ms-6">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                            <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 218.582 218.582">
                            <path d="M160.798,64.543c-1.211-1.869-2.679-3.143-4.046-4.005c-0.007-2.32-0.16-5.601-0.712-9.385
                            c0.373-4.515,1.676-29.376-13.535-40.585C133.123,3.654,122.676,0,112.294,0c-8.438,0-16.474,2.398-22.629,6.752
                            c-5.543,3.922-8.596,8.188-10.212,11.191c-4.78,0.169-14.683,2.118-19.063,14.745c-4.144,11.944-0.798,19.323,1.663,22.743
                            c-0.161,1.978-0.219,3.717-0.223,5.106c-1.367,0.862-2.835,2.136-4.046,4.005c-2.74,4.229-3.206,9.9-1.386,16.859
                            c3.403,13.012,11.344,15.876,15.581,16.451c2.61,5.218,8.346,15.882,14.086,21.24c2.293,2.14,5.274,3.946,8.86,5.37
                            c4.577,1.816,9.411,2.737,14.366,2.737s9.789-0.921,14.366-2.737c3.586-1.424,6.567-3.23,8.86-5.37
                            c5.74-5.358,11.476-16.022,14.086-21.24c4.236-0.575,12.177-3.44,15.581-16.452C164.004,74.443,163.538,68.771,160.798,64.543z
                            M152.509,78.871c-2.074,7.932-5.781,9.116-7.807,9.116c-0.144,0-0.252-0.008-0.316-0.013c-2.314-0.585-4.454,0.631-5.466,2.808
                            c-1.98,4.256-8.218,16.326-13.226,21.001c-1.377,1.285-3.304,2.425-5.726,3.386c-6.796,2.697-14.559,2.697-21.354,0
                            c-2.422-0.961-4.349-2.101-5.726-3.386c-5.008-4.675-11.246-16.745-13.226-21.001c-0.842-1.81-2.461-2.953-4.314-2.953
                            c-0.376,0-0.762,0.047-1.153,0.146c-0.064,0.006-0.172,0.013-0.315,0.013c-2.025,0-5.732-1.185-7.807-9.115
                            c-1.021-3.903-1.012-7.016,0.024-8.764c0.603-1.016,1.459-1.358,1.739-1.446c2.683-0.291,4.299-2.64,4.075-5.347
                            c-0.005-0.066-0.18-2.39,0.042-5.927c3.441-1.479,8.939-4.396,13.574-9.402c2.359-2.549,4.085-5.672,5.314-8.537
                            c3.351,2.736,8.095,5.951,14.372,8.729c10.751,4.758,32.237,7.021,41.307,7.794c0.375,4.317,0.156,7.263,0.15,7.333
                            c-0.236,2.715,1.383,5.066,4.075,5.357c0.28,0.088,1.136,0.431,1.739,1.446C153.521,71.856,153.53,74.969,152.509,78.871z
                            M184.573,145.65l-43.715-17.485c-1.258-0.502-2.665-0.473-3.903,0.08c-1.236,0.555-2.195,1.588-2.655,2.862l-10.989,30.382
                            l-2.176-6.256l3.462-8.463c0.63-1.542,0.452-3.297-0.477-4.681c-0.929-1.383-2.485-2.213-4.151-2.213H98.614
                            c-1.666,0-3.223,0.83-4.151,2.213c-0.929,1.384-1.107,3.139-0.477,4.681l3.462,8.463l-2.176,6.256l-10.989-30.382
                            c-0.46-1.274-1.419-2.308-2.655-2.862c-1.238-0.554-2.646-0.583-3.903-0.08L34.009,145.65
                            c-13.424,5.369-22.098,18.182-22.098,32.641v35.291c0,2.762,2.239,5,5,5h184.76c2.761,0,5-2.238,5-5v-35.291
                            C206.671,163.832,197.997,151.02,184.573,145.65z M183.054,192.718c0,2.762-2.239,5-5,5h-33.57c-2.761,0-5-2.238-5-5v-15.59
                            c0-2.762,2.239-5,5-5h33.57c2.761,0,5,2.238,5,5V192.718z"/>
                        </svg>
                        </span>
                        <h3 class="font-medium leading-tight">Manager</h3>
                        <p class="text-sm">${task.manager.firstName} ${task.manager.lastName}</p>

                    </li>

                    <li class="ms-6 flex items-center">
                        <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                           <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 64 64">
                                <circle cx="32" cy="32" r="5.629" />
                                <path d="M32 34.816c-1.555 0-2.816-1.261-2.816-2.816V17.2c0-1.555 1.261-2.816 2.816-2.816 1.555 0 2.816 1.261 2.816 2.816V32c0 1.555-1.261 2.816-2.816 2.816z" />
                                <path d="M24.942 41.874c-.721 0-1.441-.275-1.991-.825-1.1-1.1-1.1-2.883 0-3.982l7.058-7.058c1.099-1.1 2.883-1.1 3.982 0 1.1 1.1 1.1 2.883 0 3.982l-7.058 7.058c-.55.55-1.27.825-1.991.825z" />
                                <path d="M32 59.5C16.837 59.5 4.5 47.164 4.5 32 4.5 16.836 16.837 4.5 32 4.5S59.5 16.836 59.5 32C59.5 47.164 47.163 59.5 32 59.5zM32 10.132c-12.058 0-21.868 9.81-21.868 21.868 0 12.058 9.81 21.868 21.868 21.868S53.868 44.058 53.868 32C53.868 19.942 44.058 10.132 32 10.132z" />
                            </svg>
                        </span>

                       <h3 class="font-medium leading-tight flex justify-start items-center gap-1">
                           <p class="text-sm">${formattedStartDate}</p>
                           <p class="text-blue-400">to</p>
                           <p class="text-sm">${formattedEndDate}</p>
                       </h3>
                    </li>

                </ol>
        </div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
